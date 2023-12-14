package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrarTest {

    @Test
    public void testInserirUsuario() {
        try {
            URL randomUserApiUrl = new URL("https://randomuser.me/api/");
            HttpURLConnection randomUserConnection = (HttpURLConnection) randomUserApiUrl.openConnection();
            randomUserConnection.setRequestMethod("GET");

            int randomUserResponseCode = randomUserConnection.getResponseCode();
            assertEquals(200, randomUserResponseCode);

            BufferedReader randomUserReader = new BufferedReader(new InputStreamReader(randomUserConnection.getInputStream()));
            StringBuilder randomUserContent = new StringBuilder();
            String randomUserInputLine;

            while ((randomUserInputLine = randomUserReader.readLine()) != null) {
                randomUserContent.append(randomUserInputLine);
            }

            randomUserReader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode randomUserJson = objectMapper.readTree(randomUserContent.toString());
            JsonNode userNode = randomUserJson.at("/results/0");

            URL apiUrl = new URL("http://localhost:4567/usuarios");
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrl.openConnection();
            apiConnection.setRequestMethod("POST");
            apiConnection.setRequestProperty("Content-Type", "application/json");
            apiConnection.setDoOutput(true);

            UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
            usuarioDTOInput.setNome(userNode.at("/name/first").asText());
            usuarioDTOInput.setSenha("senha_aleatoria");

            objectMapper.writeValue(apiConnection.getOutputStream(), usuarioDTOInput);

            int apiResponseCode = apiConnection.getResponseCode();
            assertEquals(201, apiResponseCode);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
