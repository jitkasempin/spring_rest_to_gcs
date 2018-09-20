package com.jitkasem.bitcoin.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jitkasem.bitcoin.EthValue;
import com.jitkasem.bitcoin.model.Etherum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class PriceProcessor {


    private LinkedHashSet<Etherum> etherumsData = new LinkedHashSet<Etherum>();
    private long seqNumb;

    @PostConstruct
    public void postConstruct() {
        seqNumb = 0L;
    }

    public String addEtherum(Etherum eth, boolean makeJson){

        if (eth != null) {

            etherumsData.add(eth);
            if (makeJson == true) {
                Iterator<Etherum> it = etherumsData.iterator();
                List<EthValue> ethValueNumbers = new ArrayList<EthValue>();
                ObjectMapper mapper = new ObjectMapper();

                while (it.hasNext()) {
                    Etherum e = it.next();
                    Double prc = e.getCrypto().getUSD();

                    String formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .withZone(ZoneId.systemDefault())
                            .format(Instant.now());

                    EthValue v = new EthValue();
                    v.setEthPri(prc);
                    v.setTimeRecord(formattedDate);

                    ethValueNumbers.add(v);

                }

                try {

                    String jsonArray = mapper.writeValueAsString(ethValueNumbers);

                    System.out.println(jsonArray);
                    seqNumb = seqNumb + 1;
                    etherumsData.clear();

                    return jsonArray;

                } catch (JsonProcessingException je){
                    System.err.print(je.getMessage());
                } catch (IOException e) {
                    System.err.print(e.getMessage());
                }
            } else {
                return eth.toString();
            }
        }
        return "Error";
    }
}
