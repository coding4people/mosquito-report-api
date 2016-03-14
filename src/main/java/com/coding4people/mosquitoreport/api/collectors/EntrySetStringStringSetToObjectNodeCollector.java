package com.coding4people.mosquitoreport.api.collectors;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EntrySetStringStringSetToObjectNodeCollector implements Collector<Entry<String, List<String>>, ObjectNode, ObjectNode> {

    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public Supplier<ObjectNode> supplier() {
        return mapper::createObjectNode;
    }

    @Override
    public BiConsumer<ObjectNode, Entry<String, List<String>>> accumulator() {
        return (x, y) -> {
            if (!y.getValue().isEmpty())
                x.put(y.getKey(), y.getValue().get(0));
        };
    }

    @Override
    public BinaryOperator<ObjectNode> combiner() {
        return (x, y) -> {
            final Iterator<String> iterator = x.fieldNames();
            
            while (iterator.hasNext()) {
                String fieldName = iterator.next();
                y.put(fieldName, x.get(fieldName).asText());
            }
            
            return y;
        };
    }

    @Override
    public Function<ObjectNode, ObjectNode> finisher() {
        return x -> x;
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
