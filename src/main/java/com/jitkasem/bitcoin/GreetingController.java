package com.jitkasem.bitcoin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import com.jitkasem.bitcoin.model.Etherum;
import com.jitkasem.bitcoin.processor.PriceProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private GcpProjectIdProvider projectIdProvider;

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final AtomicLong fSeq = new AtomicLong();


    @Autowired
    private PriceProcessor priceProcessor;

    @RequestMapping("/greeting")
    public Etherum /*Greeting*/ greeting(@RequestParam(value="name", defaultValue="World") String name) throws IOException {


        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Etherum> response = restTemplate.exchange(
                "https://min-api.cryptocompare.com/data/pricehistorical?fsym=ETH&tsyms=BTC,USD,EUR",
                HttpMethod.GET, entity, Etherum.class);


        Etherum et = response.getBody();
        long counterWriteFile = counter.incrementAndGet();
        if ((counterWriteFile % 5) == 0) {

            long fileSequenceNumber = fSeq.incrementAndGet();

            System.out.println("Operating to write to GCS");

            String fContent = priceProcessor.addEtherum(et, true);

            String bucket = "gs://" + projectIdProvider.getProjectId();
            String gcsFilename = String.format("ethPrice_%d.json", fileSequenceNumber);

            WritableResource resource = (WritableResource)
                    context.getResource(bucket + "/" + gcsFilename);

            // Write the file to Cloud Storage using WritableResource
            try (OutputStream os = resource.getOutputStream()) {
                os.write(fContent.getBytes());
            }


        } else {
            String content = priceProcessor.addEtherum(et, false);
            System.out.println(content);
        }


        return et;

    }
}