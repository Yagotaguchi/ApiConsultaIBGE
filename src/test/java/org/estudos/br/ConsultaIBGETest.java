package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ConsultaIBGETest {
    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";



    @Test
    @DisplayName("Teste para consulta única de um distrito")
    public void testConsultarDistrito() throws IOException {
        // Arrange
        int id = 12345;

        // Act
        String resposta = ConsultaIBGE.consultarDistrito(id);

        // Assert
        // Verifica se a resposta não está vazia
        assertNotNull(resposta, "A resposta da consulta não deve ser nula.");

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste para verificar exceção durante a consulta")
    public void testConsultarEstado_Exception() throws IOException {
        // Arrange
        String uf = "XY"; // Uma sigla de estado inválida

        // Act & Assert
        // Verifica se uma exceção é lançada ao consultar um estado inválido
        ConsultaIBGE.consultarEstado(uf);
    }

        @Test
        @DisplayName("Teste com Mockito para verificar a conexão")
        public void testConsultarEstado_Mockito() throws IOException {
            // Arrange
            String uf = "RJ";


            String respostaMockada = "{\"id\":33,\"sigla\":\"RJ\",\"nome\":\"Rio de Janeiro\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";


            // Mock da HttpURLConnection
            HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
            BufferedReader mockReader = Mockito.mock(BufferedReader.class);

            // Configurando o comportamento do mock
            when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(respostaMockada.getBytes()));
            when(mockReader.readLine()).thenReturn(respostaMockada).thenReturn(null); // Simulando uma única linha de resposta

            // Mock da URL
            URL mockUrl = Mockito.mock(URL.class);
            when(mockUrl.openConnection()).thenReturn(mockConnection);

            // Act
            String resposta = ConsultaIBGE.consultarEstado(uf);

            // Assert
            assertEquals(respostaMockada, resposta, "A resposta deve ser igual à resposta mockada");
        }
}


