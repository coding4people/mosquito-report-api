package com.coding4people.mosquitoreport.api.collectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EntrySetStringStringSetToObjectNodeCollectorTest {

    @Test
    public void testCombine() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode a = mapper.createObjectNode().put("a", 1);
        ObjectNode b = mapper.createObjectNode().put("b", 2).put("c", 3);
        ObjectNode c = new EntrySetStringStringSetToObjectNodeCollector().combiner().apply(a, b);
        
        assertEquals(1, c.get("a").asInt());
        assertEquals(2, c.get("b").asInt());
        assertEquals(3, c.get("c").asInt());
    }
}
