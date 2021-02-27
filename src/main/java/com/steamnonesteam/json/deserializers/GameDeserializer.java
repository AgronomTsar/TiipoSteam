package com.steamnonesteam.json.deserializers;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.steamnonesteam.model.Game;
import java.io.IOException;
public class GameDeserializer extends StdDeserializer<Game> {
    public GameDeserializer() {
        super(Game.class);
    }
    @Override
    public Game deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("id").asInt();
        String name=root.get("name").asText();
        String description=root.get("description").asText();
        String price=root.get("price").asText();
        return new Game(id,name,description,price);
    }
}
