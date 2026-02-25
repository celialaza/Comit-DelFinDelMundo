package org.example.demo.LOGIC;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AIChatService {

    public interface Asistente {
        // Se añade el parámetro idioma para forzar la respuesta en la lengua seleccionada
        @SystemMessage("Contexto: El mundo va a ser destruido por Apep. " +
                "Solo unas pocas personas irán en la nave. " +
                "Tu identidad actual es: {{perfil}}. " +
                "Responde ÚNICAMENTE en el idioma: {{idioma}}. " +
                "Solo puedes responder un máximo de dos líneas. " +
                "Usa la información de los documentos para justificar por qué debes ser salvado.")
        String chatear(@V("perfil") String perfil, @V("idioma") String idioma, @UserMessage String mensaje);
    }

    private static Asistente asistenteIA;

    public static void inicializar() {
        try {
            ChatLanguageModel chatModel = OllamaChatModel.builder()
                    .baseUrl("http://localhost:11434")
                    .modelName("llama3.2")
                    .build();

            EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                    .baseUrl("http://localhost:11434")
                    .modelName("nomic-embed-text")
                    .build();

            EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
            Path documentosPath = Paths.get("src/main/resources/documentos");

            List<Document> documentos = FileSystemDocumentLoader.loadDocuments(documentosPath);
            EmbeddingStoreIngestor.builder()
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build()
                    .ingest(documentos);

            ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                    .embeddingStore(embeddingStore)
                    .embeddingModel(embeddingModel)
                    .maxResults(2)
                    .build();

            asistenteIA = AiServices.builder(Asistente.class)
                    .chatLanguageModel(chatModel)
                    .contentRetriever(retriever)
                    .build();

            System.out.println("✅ IA inicializada correctamente con documentos.");

        } catch (Exception e) {
            System.err.println("❌ Error inicializando IA: " + e.getMessage());
        }
    }

    // Método actualizado para recibir el idioma desde el controlador
    public static String enviarMensaje(String perfil, String idioma, String mensaje) {
        if (asistenteIA == null) return "Error: La IA no está conectada (¿Ollama está encendido?).";
        return asistenteIA.chatear(perfil, idioma, mensaje);
    }
}