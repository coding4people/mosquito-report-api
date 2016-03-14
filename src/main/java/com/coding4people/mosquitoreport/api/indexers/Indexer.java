package com.coding4people.mosquitoreport.api.indexers;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;

import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;
import com.amazonaws.services.cloudsearchdomain.model.QueryParser;
import com.amazonaws.services.cloudsearchdomain.model.SearchRequest;
import com.amazonaws.services.cloudsearchdomain.model.SearchResult;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsRequest;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.DescribeDomainsRequest;
import com.amazonaws.services.cloudsearchv2.model.DomainStatus;
import com.amazonaws.services.cloudsearchv2.model.ServiceEndpoint;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.coding4people.mosquitoreport.api.Env;
import com.coding4people.mosquitoreport.api.collectors.EntrySetStringStringSetToObjectNodeCollector;
import com.coding4people.mosquitoreport.api.models.Searchable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Singleton
abstract public class Indexer<T extends Searchable> {
    @Inject
    AmazonCloudSearch amazonCloudSearch;

    @Inject
    Env env;

    @Inject
    ExecutorService executor;
    
    @Inject
    ObjectMapper objectMapper;

    AmazonCloudSearchDomain domain;

    abstract protected Class<T> getType();

    public void index(T item) {
        this.index(Arrays.asList(item));
    }

    // TODO send to SQS in order to handle it asynchronously
    // TODO implement recover and fallback
    public void index(List<T> items) {
        executor.submit(() -> indexSync(items));
    }

    private void indexSync(List<T> items) {
        try {
            String json = objectMapper.writeValueAsString(items.stream().map(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("type", "add");
                map.put("id", item.getSearchId());
                map.put("fields", item);
                return map;
            }).collect(Collectors.toList()));

            domain.uploadDocuments(new UploadDocumentsRequest().withContentType("application/json")
                    .withContentLength(Long.valueOf(json.length()))
                    .withDocuments(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        } catch (JsonProcessingException e) {
            // TODO log indexer errors
            e.printStackTrace();
        }
    }

    // TODO avoid query injection
    public List<ObjectNode> search(String latlonnw, String latlonse) {
        String[] latlonnwa = latlonnw.split(",");
        String[] latlonsea = latlonse.split(",");

        Double latnw = Double.parseDouble(latlonnwa[0]);
        Double lonnw = Double.parseDouble(latlonnwa[1]);

        Double latse = Double.parseDouble(latlonsea[0]);
        Double lonse = Double.parseDouble(latlonsea[1]);

        String latlon = (latnw - ((latnw - latse) / 2)) + "," + (lonnw - ((lonnw - lonse) / 2));

        SearchRequest request = new SearchRequest().withSize(30L)
                .withQuery("latlon:['" + latlonnw + "','" + latlonse + "']")
                .withExpr("{\"distance\":\"haversin(" + latlon + ",latlon.latitude,latlon.longitude)\"}")
                .withSort("distance asc");

        request.setQueryParser(QueryParser.Structured);

        SearchResult result = domain.search(request);
        
        return result.getHits().getHit().stream().map(hit -> {
            return hit.getFields().entrySet().stream().collect(new EntrySetStringStringSetToObjectNodeCollector());
        }).collect(Collectors.toList());
    }

    private Double limitLat(Double d) {
        Double max = 89.899D;
        //Does not include 89.899
        if (d >= max) return d - (max * 2);
        if (d <= -max) return d + (max * 2);
        return d;
    }
    
    private Double limitLon(Double d) {
        Double max = 180D;
        //Includes 180
        if (d > max) return d - (max * 2);
        if (d < -max) return d + (max * 2);
        return d;
    }
    
    // TODO avoid query injection
    public List<ObjectNode> searchCenter(String latlon) {
        String[] latlonnwa = latlon.split(",");

        Double range = 0.1D;
        
        Double lat = Double.parseDouble(latlonnwa[0]);
        Double lon = Double.parseDouble(latlonnwa[1]);

        Double latnw = limitLat(lat + range);
        Double lonnw = limitLon(lon - range);
        Double latse = limitLat(lat - range);
        Double lonse = limitLon(lon + range);
        
        return search(latnw + "," + lonnw, latse + "," + lonse);
    }

    @PostConstruct
    protected void postConstruct() {
        DescribeDomainsRequest describeDomainsRequest = new DescribeDomainsRequest().withDomainNames(getDomainName());
        List<DomainStatus> list = amazonCloudSearch.describeDomains(describeDomainsRequest).getDomainStatusList();

        if (list.isEmpty()) {
            throw new InternalServerErrorException("Could not find CloudSearch domain: " + getDomainName());
        }

        ServiceEndpoint searchService = list.get(0).getSearchService();

        if (searchService == null || searchService.getEndpoint() == null) {
            throw new InternalServerErrorException("Could not find SearchService for: " + getDomainName());
        }
        
        domain = createDomain();
        domain.setEndpoint(searchService.getEndpoint());
    }
    
    protected AmazonCloudSearchDomain createDomain() {
        return new AmazonCloudSearchDomainClient();
    }

    protected String getDomainName() {
        return getPrefix() + getType().getAnnotation(DynamoDBTable.class).tableName();
    }

    protected String getPrefix() {
        return env.get("CLOUDSEARCH_DOMAIN_PREFIX").orElse("localhost") + "-";
    }
}
