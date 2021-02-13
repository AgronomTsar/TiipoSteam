package com.steamnonesteam.json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.steamnonesteam.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDeserializer extends StdDeserializer<User> {
    public UserDeserializer() {
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("id").asInt();
        String fname=root.get("fname").asText();
        String lname=root.get("lname").asText();
        String nickName=root.get("nickName").asText();
        String birthday=root.get("birthday").asText();
        DateTimeFormatter f=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate bday=LocalDate.parse(birthday,f);
        String password=root.get("password").asText();
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        String email = root.get("email").asText();
        String role= root.get("role").asText();
        return new User(id,fname,lname,nickName,bday,hashed,email,role);
    }
}
