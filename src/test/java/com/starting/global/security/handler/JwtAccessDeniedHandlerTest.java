package com.starting.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starting.domain.member.controller.UserMemberController;
import com.starting.domain.member.service.UserMemberService;
import com.starting.global.response.ResponseService;
import com.starting.global.security.JwtProvider;
import com.starting.global.security.SecurityConfig;
import com.starting.global.security.UserDetailsServiceImpl;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@ContextConfiguration(classes = {UserMemberController.class, SecurityConfig.class, ObjectMapper.class})
@WebAppConfiguration
@AutoConfigureMockMvc
class JwtAccessDeniedHandlerTest {

    JwtAccessDeniedHandler jwtAccessDeniedHandler;

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
        jwtAccessDeniedHandler = new JwtAccessDeniedHandler(new ResponseService(), new ObjectMapper());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void handle_method() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AccessDeniedException ex = new AccessDeniedException("");

        jwtAccessDeniedHandler.handle(request, response, ex);
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void handle_request() throws Exception {
        mockMvc.perform(get("/test")
                .with(user("1234").password("1234").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}