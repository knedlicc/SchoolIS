package cz.cvut.rsp.help.school.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.rsp.help.school.dto.JwtTokenDto;
import cz.cvut.rsp.help.school.dto.Person.AddUserDto;
import cz.cvut.rsp.help.school.dto.Person.AuthDto;
import cz.cvut.rsp.help.school.dto.Person.PersonMngDto;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.service.JWTAuthService;
import cz.cvut.rsp.help.school.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Mock
    private PersonService personServiceMock;

    @Mock
    private JWTAuthService jwtAuthServiceMock;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(this.personServiceMock, this.jwtAuthServiceMock)).build();
    }


    @Test
    public void testRegister_invalidDto_expectBadRequest() throws Exception {
        AuthDto authDto = new AuthDto("asd", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String token = "hello-world";
        when(jwtAuthServiceMock.auth(any())).thenReturn(token);

        this.mockMvc.perform(
            post("/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(authDto)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_expectATokenInBody() throws Exception {
        Person person = Generator.generatePerson();
        AuthDto authDto = new AuthDto(person.getEmail(), person.getPassword());
        ObjectMapper objectMapper = new ObjectMapper();
        String token = "hello-world";
        when(jwtAuthServiceMock.auth(any())).thenReturn(token);

        MvcResult mvcResult = this.mockMvc.perform(
            post("/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(authDto)))
            .andDo(print()).andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        JwtTokenDto jwtTokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JwtTokenDto.class);
        assertNotNull(jwtTokenDto);
        assertEquals(token, jwtTokenDto.getToken());
    }

    @Test
    public void getUserByEmailTest_userIsPresented() throws Exception {
        Person person = Generator.generatePerson();
        ObjectMapper objectMapper = new ObjectMapper();

        when(personServiceMock.findByEmailOrThrow(person.getEmail())).thenReturn(person);

        var r = get("/v1/users/" + person.getEmail() + "/")
            .accept(MediaType.APPLICATION_JSON_VALUE);
        MvcResult mvcResult = this.mockMvc.perform(r)
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        PersonMngDto personDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonMngDto.class);

        assertEquals(person.getEmail(), personDto.getEmail());
        assertEquals(person.getFirstName(), personDto.getFirstName());
    }

    @Test
    public void updateTest_userIsUpdated() throws Exception {
        Person person = Generator.generatePerson();
        AddUserDto personMngDto = new AddUserDto();
        personMngDto.setFrom(person);
        ObjectMapper objectMapper = new ObjectMapper();
        personMngDto.setName("Test");

        when(personServiceMock.findByEmailOrThrow(person.getEmail())).thenReturn(person);
        when(personServiceMock.update(person)).thenReturn(person);

        MvcResult mvcResult = this.mockMvc.perform(
            post("/v1/users/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(personMngDto)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        AddUserDto personDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AddUserDto.class);

        assertEquals(person.getEmail(), personDto.getEmail());
        assertEquals(person.getFirstName(), personDto.getName());
    }

    @Test
    public void deleteUserTest_userIsDeleted() throws Exception {
        Person person = Generator.generatePerson();

        when(personServiceMock.findByEmailOrThrow(person.getEmail())).thenReturn(person);

        MvcResult mvcResult = this.mockMvc.perform(
            delete("/v1/users/"+person.getEmail()+"/"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertTrue(response.contains(person.getEmail()));
    }

    @Test
    public void userData_ok() throws Exception {
        Person person = Generator.generatePerson();
        ObjectMapper objectMapper = new ObjectMapper();

        when(personServiceMock.getCurrentPerson()).thenReturn(person);

        MvcResult mvcResult = this.mockMvc.perform(
            get("/v1/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        PersonMngDto personDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonMngDto.class);

        assertEquals(person.getEmail(), personDto.getEmail());
        assertEquals(person.getFirstName(), personDto.getFirstName());
        assertEquals(person.getLastName(), personDto.getLastName());
    }
}
