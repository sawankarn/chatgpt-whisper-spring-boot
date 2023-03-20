
// import necessary libraries
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.github.javapuzzle.service.openaiclient.model.request.ChatGPTRequest;
import com.github.javapuzzle.service.openaiclient.model.request.ChatRequest;
import com.github.javapuzzle.service.openaiclient.model.request.TranscriptionRequest;
import com.github.javapuzzle.service.openaiclient.model.request.WhisperTranscriptionRequest;
import com.github.javapuzzle.service.openaiclient.model.request.Message;
import com.github.javapuzzle.service.openaiclient.model.response.ChatGPTResponse;
import com.github.javapuzzle.service.openaiclient.model.response.WhisperTranscriptionResponse;
import com.github.javapuzzle.service.openaiclient.openaiclient.OpenAIClient;
import com.github.javapuzzle.service.openaiclient.openaiclient.OpenAIClientConfig;
import com.github.javapuzzle.service.openaiclient.service.OpenAIClientService;

public class OpenAIClientServiceTest {

   // instantiate openAIClient and openAIClientConfig mocks
   @Mock
   private OpenAIClient openAIClient;
   @Mock
   private OpenAIClientConfig openAIClientConfig;
   
   // instantiate OpenAIClientService instance
   @InjectMocks
   private OpenAIClientService openAIClientService;
   
   // initialize mock objects
   @BeforeEach
   public void init(){
       MockitoAnnotations.openMocks(this);
   }
   
   // test the chat method of openAIClientService
   @Test
   public void testChat(){
       // create a chatRequest instance
       ChatRequest chatRequest = ChatRequest
               .builder()
               .question("what is your name?")
               .build();
       // create a mock chatGPTRequest and chatGPTResponse instance
       ChatGPTRequest chatGPTRequest = ChatGPTRequest
               .builder()
               .model("model")
               .messages(Collections.singletonList(Message.builder().content("what is your name?").role("user").build()))
               .build();
       ChatGPTResponse chatGPTResponse = ChatGPTResponse
               .builder()
               .responseText("My name is Siri")
               .build();
       // mock the openAIClient.chat method
       when(openAIClient.chat(chatGPTRequest)).thenReturn(chatGPTResponse);
       // call the openAIClientService.chat() method
       ChatGPTResponse cgr = openAIClientService.chat(chatRequest);
       // test if chat method of openAIClient is called only once
       verify(openAIClient, times(1)).chat(chatGPTRequest);

       // test if returned chatGPTResponse is the same as the mocked response
       Assertions.assertEquals("My name is Siri", cgr.getResponseText());
   }

   // test the createTranscription method of openAIClientService
   @Test
   public void testCreateTranscription() {
       // create a TranscriptionRequest instance
       TranscriptionRequest transcriptionRequest = TranscriptionRequest
               .builder()
               .file("audiofile")
               .build();
       // create a mock WhisperTranscriptionRequest and WhisperTranscriptionResponse
       WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest
               .builder()
               .model("audio model")
               .file("audiofile")
               .build();
       WhisperTranscriptionResponse whisperTranscriptionResponse = WhisperTranscriptionResponse
               .builder()
               .result("Hello World")
               .build();

       // mock the openAIClient.createTranscription method
       when(openAIClient.createTranscription(whisperTranscriptionRequest))
               .thenReturn(whisperTranscriptionResponse);

       // call the openAIClientService.createTranscription method
       WhisperTranscriptionResponse wtr = openAIClientService.createTranscription(transcriptionRequest);

       // test if createTranscription method of openAIClient is called only once
       verify(openAIClient, times(1)).createTranscription(whisperTranscriptionRequest);

       // test if returned WhisperTranscriptionResponse is the same as the mocked response
       Assertions.assertEquals("Hello World", wtr.getResult());
   }
}
