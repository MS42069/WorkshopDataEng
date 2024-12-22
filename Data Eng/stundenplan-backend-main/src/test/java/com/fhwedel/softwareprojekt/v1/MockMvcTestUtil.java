package com.fhwedel.softwareprojekt.v1;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

@Component
public class MockMvcTestUtil {
    @Autowired MockMvc mockMvc;

    public static MockHttpServletRequestBuilder post(String url, Object payload)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.post(url)
                .content(getObjectMapper().writeValueAsString(payload))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());
    }

    public static MockHttpServletRequestBuilder patch(String url, Object payload)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.patch(url)
                .content(getObjectMapper().writeValueAsString(payload))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());
    }

    public static MockHttpServletRequestBuilder get(String url) throws JsonProcessingException {
        return MockMvcRequestBuilders.get(url).with(csrf());
    }

    public static MockHttpServletRequestBuilder delete(String url) throws JsonProcessingException {
        return MockMvcRequestBuilders.delete(url).with(csrf());
    }

    public static MockHttpServletRequestBuilder put(String url, Object payload)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.put(url)
                .content(getObjectMapper().writeValueAsString(payload))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public static <E> E mapResult(MvcResult result, Class<E> type) throws Exception {
        String response = result.getResponse().getContentAsString();
        return getObjectMapper().readValue(response, type);
    }

    public <E> E post(String url, Class<E> type, Object body) throws Exception {
        String response =
                mockMvc.perform(post(url, body)).andReturn().getResponse().getContentAsString();
        return getObjectMapper().readValue(response, type);
    }

    public <E> E put(String url, Class<E> type, Object body) throws Exception {
        String response =
                mockMvc.perform(put(url, body)).andReturn().getResponse().getContentAsString();
        return getObjectMapper().readValue(response, type);
    }

    public <E> E patch(String url, Class<E> type, Object body) throws Exception {
        String response =
                mockMvc.perform(patch(url, body)).andReturn().getResponse().getContentAsString();
        return getObjectMapper().readValue(response, type);
    }

    public <E> E get(String url, Class<E> type) throws Exception {
        String response = mockMvc.perform(get(url)).andReturn().getResponse().getContentAsString();
        return getObjectMapper().readValue(response, type);
    }

    public <E> E getWithRequestParams(
            String url, MultiValueMap<String, String> requestParams, Class<E> type)
            throws Exception {
        String response =
                mockMvc.perform(get(url).params(requestParams))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        return getObjectMapper().readValue(response, type);
    }

    public <E> E delete(String url, Class<E> type) throws Exception {
        String response =
                mockMvc.perform(delete(url)).andReturn().getResponse().getContentAsString();
        return getObjectMapper().readValue(response, type);
    }
}
