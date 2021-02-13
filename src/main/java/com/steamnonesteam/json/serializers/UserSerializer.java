package com.steamnonesteam.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.steamnonesteam.model.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserSerializer extends StdSerializer<User>{

    public UserSerializer() {
        super(User.class);
    }

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",user.getId());
        gen.writeStringField("fname",user.getFname());
        gen.writeStringField("lname",user.getLname());
        gen.writeStringField("nickname",user.getNickname());
        DateTimeFormatter f=DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        gen.writeStringField("birthday",user.getBirthday().format(f));
        gen.writeStringField("email",user.getEmail());
        gen.writeStringField("role",user.getRole());
        gen.writeEndObject();
    }
}
