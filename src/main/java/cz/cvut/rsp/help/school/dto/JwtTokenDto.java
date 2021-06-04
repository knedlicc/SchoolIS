package cz.cvut.rsp.help.school.dto;


public class JwtTokenDto {

    private String token;


    public JwtTokenDto() {
    }

    public JwtTokenDto(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    public JwtTokenDto setToken(String token) {
        this.token = token;
        return this;
    }

}
