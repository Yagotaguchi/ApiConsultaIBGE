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
        int id = 34243;

        // Act
        String resposta = ConsultaIBGE.consultarDistrito(id);

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

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


}


