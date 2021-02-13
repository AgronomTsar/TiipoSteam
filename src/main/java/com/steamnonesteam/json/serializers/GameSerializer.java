package com.steamnonesteam.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.steamnonesteam.model.Game;

import java.io.IOException;

public class GameSerializer extends StdSerializer<Game> {
    public GameSerializer() {
        super(Game.class);
    }
    @Override
    public void serialize(Game game, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",game.getId());
        gen.writeStringField("name",game.getName());
        gen.writeStringField("description",game.getDescription());
        gen.writeStringField("price",game.getPrice());
        gen.writeEndObject();
    }
}
