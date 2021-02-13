package com.steamnonesteam.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.steamnonesteam.model.UserGame;

import java.io.IOException;

public class UserGameSerializer extends StdSerializer<UserGame> {
    public UserGameSerializer() {
        super(UserGame.class);
    }

    @Override
    public void serialize(UserGame userGame, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",userGame.getId());
        gen.writeNumberField("userId",userGame.getUserId().getId());
        gen.writeNumberField("gameId",userGame.getGameId().getId());
        gen.writeEndObject();
    }
}
