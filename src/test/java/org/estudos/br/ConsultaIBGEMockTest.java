package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultaIBGEMockTest {

    // Mock para simular a conexão HTTP
    @Mock
    private HttpURLConnection connectionMock;

    // JSON de resposta simulada
    private static final String JSON_RESPONSE_RJ = "{\"id\":33,\"sigla\":\"RJ\",\"nome\":\"Rio de Janeiro\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";
    @BeforeEach
    public void setup() throws IOException {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE_RJ.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    // Teste para verificar se o método consultarDistrito retorna o JSON esperado para o estado de São Paulo
    @Test
    @DisplayName("Consulta usando o Mock")
    public void testConsultarEstadoComMock() throws IOException {
        // Sigla do estado a ser consultado
        String estado = "RJ";

        // Act (Execução do método a ser testado)
        String response = ConsultaIBGE.consultarEstado(estado);

        // Verificamos se o JSON retornado é o mesmo que o JSON de resposta simulada
        assertEquals(JSON_RESPONSE_RJ, response, "O JSON retornado não corresponde a resposta esperada.");
    }

}