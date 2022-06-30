package com.starteeing.golbal.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starteeing.domain.member.controller.UserMemberController;
import com.starteeing.domain.member.service.UserMemberService;
import com.starteeing.golbal.response.ResponseService;
import com.starteeing.golbal.security.JwtProvider;
import com.starteeing.golbal.security.SecurityConfig;
import com.starteeing.golbal.security.UserDetailsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@ContextConfiguration(classes = {UserMemberController.class, SecurityConfig.class, ObjectMapper.class})
@WebAppConfiguration
@AutoConfigureMockMvc
class JwtAuthenticationEntryPointTest {

    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    WebApplicationContext applicationContext;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtProvider jwtProvider;
    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    UserMemberService userMemberService;
    @MockBean
    ResponseService responseService;

    @BeforeEach
    void setUp() {
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(new ResponseService(), new ObjectMapper());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void commence_method() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException ex = new AuthenticationCredentialsNotFoundException("");

        jwtAuthenticationEntryPoint.commence(request, response, ex);
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void commence_request() throws Exception {
        mockMvc.perform(get("/test")
                .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}