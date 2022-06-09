package com.example.workflow.api;

import com.example.workflow.ermis.ProcessingStatus;
import com.example.workflow.ermis.ProcessingStatusRepository;
import com.example.workflow.kafka.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class StartProcessController {

//    @Autowired
//    RuntimeService runtimeService;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ProcessingStatusRepository repository;

    @GetMapping("/ermis/validate")
    public String startProcess() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("example.json");
        kafkaTemplate.send("cwm.validation.validation-requested", "22DKPCCAEBGVUBDER5", getContent(stream));
        return "Process Started";
    }

    @GetMapping("/load/start/{count}")
    public String initiateValidation(@PathVariable(name = "count") int count) {
        ArrayList<String> mrnList = new ArrayList<>();

        for(int x = 0; x < count; x++){
            String mrn = x + "IDLOAD" + System.currentTimeMillis();
            var processingStatus = new ProcessingStatus();
            processingStatus.setDeclaration_id(mrn);
            processingStatus.setProcessing_status_type(1L);
            processingStatus.setDeclaration_version(1L);
            repository.save(processingStatus);
            mrnList.add(mrn);
        }

        for(String mrn : mrnList){
            kafkaTemplate.send("cwm.validation.validation-requested", getMessageGenerator(mrn));
            System.out.println("Created request with mrn: " + mrn);
        }
        return "Process Started";
    }

    public String getMessageGenerator(String businessKey) {
        UUID uuid = UUID.randomUUID();
        return "{\"payload\":\"{\\\"declartionId\\\":\\\""+businessKey+"\\\",\\\"declarationVersion\\\":1,\\\"declaration\\\":{\\\"messageID\\\":\\\""+  uuid+"\\\",\\\"declarationID\\\":\\\"" + businessKey + "\\\",\\\"submitterReference\\\":\\\"EM220357\\\",\\\"version\\\":1,\\\"modelVersion\\\":\\\"2.0\\\",\\\"receiveDate\\\":[2022,3,23,10,32,47,615000000],\\\"goodsItemCount\\\":{\\\"value\\\":1},\\\"tradeMovementType\\\":\\\"IM\\\",\\\"type\\\":\\\"D\\\",\\\"incidents\\\":[],\\\"customsOfficeRoles\\\":[{\\\"customsOfficeRoleType\\\":\\\"96\\\",\\\"customsOfficeID\\\":\\\"DK004700\\\"},{\\\"customsOfficeRoleType\\\":\\\"186\\\",\\\"customsOfficeID\\\":\\\"DK004700\\\"}],\\\"countryRegionRoles\\\":[],\\\"parties\\\":[{\\\"partyRoleType\\\":\\\"DT\\\",\\\"partyIdentification\\\":\\\"DK36012350\\\",\\\"partyIdentificationType\\\":\\\"1\\\",\\\"physicalAddress\\\":{\\\"sequenceNumber\\\":1},\\\"party\\\":{\\\"identificationNumber\\\":\\\"DK36012350\\\",\\\"addresses\\\":[{\\\"sequenceNumber\\\":1}],\\\"bankAccounts\\\":[],\\\"partyIdentificationType\\\":\\\"1\\\",\\\"isAuthorizedForVatTransfer\\\":false,\\\"communications\\\":[]}},{\\\"partyRoleType\\\":\\\"TB\\\",\\\"partyName\\\":\\\"swp.tdp01.b2b\\\",\\\"partyIdentification\\\":\\\"TP000000021\\\",\\\"partyIdentificationType\\\":\\\"1\\\",\\\"party\\\":{\\\"identificationNumber\\\":\\\"TP000000021\\\",\\\"name\\\":\\\"swp.tdp01.b2b\\\",\\\"addresses\\\":[],\\\"bankAccounts\\\":[],\\\"partyIdentificationType\\\":\\\"1\\\",\\\"isAuthorizedForVatTransfer\\\":false,\\\"communications\\\":[]}},{\\\"partyRoleType\\\":\\\"EX\\\",\\\"partyName\\\":\\\"Exporter Name\\\",\\\"partyIdentificationType\\\":\\\"1\\\",\\\"physicalAddress\\\":{\\\"sequenceNumber\\\":1,\\\"cityName\\\":\\\"Oslo\\\",\\\"countryCode\\\":\\\"NO\\\",\\\"streetAndNumber\\\":\\\"Normal street\\\",\\\"zipCode\\\":\\\"1345\\\"},\\\"party\\\":{\\\"name\\\":\\\"Exporter Name\\\",\\\"addresses\\\":[{\\\"sequenceNumber\\\":1,\\\"cityName\\\":\\\"Oslo\\\",\\\"countryCode\\\":\\\"NO\\\",\\\"streetAndNumber\\\":\\\"Normal street\\\",\\\"zipCode\\\":\\\"1345\\\"}],\\\"bankAccounts\\\":[],\\\"partyIdentificationType\\\":\\\"1\\\",\\\"isAuthorizedForVatTransfer\\\":false,\\\"communications\\\":[]}}],\\\"amendments\\\":[],\\\"currencies\\\":[],\\\"remarks\\\":[],\\\"consignmentShipment\\\":[{\\\"extensions\\\":[],\\\"sequenceNumber\\\":0,\\\"goodsItems\\\":[{\\\"sequenceNumber\\\":1,\\\"commodity\\\":{\\\"sequenceNumber\\\":1,\\\"description\\\":\\\"Single focus spectacle lenses of glass.\\\",\\\"classifications\\\":[{\\\"sequenceNumber\\\":1,\\\"identifier\\\":\\\"900140\\\",\\\"commodityClassificationType\\\":\\\"HS\\\"}],\\\"supplementaryUnits\\\":{},\\\"grossMass\\\":{\\\"measureUnitType\\\":\\\"KGM\\\",\\\"value\\\":10},\\\"netMass\\\":{\\\"measureUnitType\\\":\\\"KGM\\\"},\\\"containers\\\":[]},\\\"countryRegionRoles\\\":[{\\\"countryRegionRoleType\\\":\\\"5\\\"},{\\\"countryRegionRoleType\\\":\\\"1\\\"},{\\\"countryRegionRoleType\\\":\\\"2\\\"}],\\\"parties\\\":[],\\\"packaging\\\":[{\\\"sequenceNumber\\\":1,\\\"width\\\":{},\\\"length\\\":{},\\\"height\\\":{},\\\"volume\\\":{},\\\"quantity\\\":{\\\"value\\\":1}}],\\\"particulars\\\":[],\\\"additionalInformation\\\":[],\\\"additionalDocuments\\\":[],\\\"precedingDocuments\\\":[],\\\"additionalUnits\\\":[],\\\"extensions\\\":[],\\\"declaredDutyTaxFees\\\":[],\\\"procedureCombination\\\":{\\\"previousProcedureType\\\":\\\"00\\\",\\\"requestedProcedureType\\\":\\\"40\\\",\\\"specialProcedures\\\":[\\\"C07\\\"]},\\\"valuationAdjustments\\\":[],\\\"declaredCustomsValue\\\":{\\\"measureUnitType\\\":\\\"DKK\\\",\\\"value\\\":100},\\\"postalCharges\\\":{\\\"measureUnitType\\\":\\\"DKK\\\"},\\\"apportionedPostalCharges\\\":{}}],\\\"containers\\\":[],\\\"customsWarehouse\\\":{\\\"warehouse\\\":{}},\\\"tradeTerms\\\":{},\\\"transportMeans\\\":[{\\\"transportMeansRoleType\\\":\\\"5\\\",\\\"itineraries\\\":[]},{\\\"transportMeansRoleType\\\":\\\"1\\\",\\\"itineraries\\\":[]}],\\\"countryRegionRoles\\\":[{\\\"countryRegionRoleType\\\":\\\"5\\\"}],\\\"customsOfficeRoles\\\":[],\\\"locations\\\":[{\\\"locationRoleType\\\":\\\"14\\\",\\\"locationId\\\":\\\"DKFDH-0003\\\",\\\"dateTimes\\\":[],\\\"locationIdentificationType\\\":\\\"U\\\",\\\"locationType\\\":\\\"A\\\",\\\"physicalAddress\\\":{},\\\"coordinates\\\":{},\\\"personContactDetails\\\":{}},{\\\"locationRoleType\\\":\\\"9\\\",\\\"dateTimes\\\":[]}],\\\"parties\\\":[{\\\"partyRoleType\\\":\\\"IM\\\",\\\"partyIdentification\\\":\\\"DK36012350\\\",\\\"partyIdentificationType\\\":\\\"1\\\",\\\"physicalAddress\\\":{\\\"sequenceNumber\\\":1},\\\"party\\\":{\\\"identificationNumber\\\":\\\"DK36012350\\\",\\\"addresses\\\":[{\\\"sequenceNumber\\\":1}],\\\"bankAccounts\\\":[],\\\"partyIdentificationType\\\":\\\"1\\\",\\\"isAuthorizedForVatTransfer\\\":false,\\\"communications\\\":[]}}],\\\"additionalInformation\\\":[],\\\"invoice\\\":{},\\\"ucr\\\":{},\\\"specificCircumstanceType\\\":\\\"E\\\",\\\"postalCharges\\\":{\\\"measureUnitType\\\":\\\"DKK\\\",\\\"value\\\":100},\\\"previousDocuments\\\":[],\\\"valuationAdjustments\\\":[]}],\\\"modeOfEntryType\\\":\\\"2\\\",\\\"totalGrossMass\\\":{\\\"measureUnitType\\\":\\\"KGM\\\",\\\"value\\\":10},\\\"extensions\\\":[],\\\"guarantees\\\":[],\\\"additionalInformation\\\":[],\\\"procedureCategory\\\":\\\"H7\\\",\\\"requestedProcedureCategory\\\":\\\"H7\\\",\\\"subsequentEntryOffices\\\":[],\\\"signature\\\":{\\\"timeStamp\\\":[2022,3,23,10,32,47]},\\\"additionalDocuments\\\":[],\\\"previousDocuments\\\":[],\\\"transportMeans\\\":[]}}\",\"eventId\":\"b38ac055-3426-4541-9b07-8f68020908df\",\"correlatedEventId\":null,\"creationDate\":1648031567957,\"expirationDate\":0,\"sourceId\":\"process-manager\",\"userId\":null,\"businessKey\":\"72DKLHQUDBP38VLGR7\",\"properties\":null,\"eventType\":\"ValidationRequestedCommand\",\"payloadVersion\":\"1\",\"traceId\":null}";
    }

    private String getContent(InputStream stream){
        StringBuilder contents = new StringBuilder();
        try (InputStreamReader isr = new InputStreamReader(stream);
             BufferedReader br = new BufferedReader(isr);)
        {
            String line;
            while ((line = br.readLine()) != null) {
                contents.append(line);
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contents.toString();
    }

    @GetMapping("/start/{businessKey}/{count}")
    public String startProcess(@PathVariable(name = "businessKey") String businessKey,
                               @PathVariable(name = "count") int count) {
        for (int i = 1; i <= count; i++) {
            String id = businessKey + "-" + i;
            kafkaTemplate.send(Topic.PROCESS_START.getTopic(), id);
        }
        return "Process Started";
    }

    @GetMapping("/start/kafka/{processKey}/{businessKey}")
    public String startProcessThroughKafka(@PathVariable(name = "processKey") String processKey,
                               @PathVariable(name = "businessKey") String businessKey) {
        kafkaTemplate.send(Topic.PROCESS_START.getTopic(), "test");
        return "Kafka message produced";
    }


    @GetMapping("/correlate/{messageName}/{businessKey}")
    public String correlateMessage(@PathVariable(name = "messageName") String messageName,
                                   @PathVariable(name = "businessKey") String businessKey) {
//        runtimeService.correlateMessage(messageName, businessKey);
        return "Message correlated";
    }

    @GetMapping("/get-active/{processId}")
    public String getActive(@PathVariable(name = "processId") String processId) {
        try{
//            List<String> activeActivityIds = runtimeService.getActiveActivityIds(processId);
            return "Active flows: ";
        }catch(Exception e){
            return "Error happened... " + e.getMessage();
        }
    }


    @GetMapping("/update-variable/{processId}")
    public String correlateMessage(@PathVariable(name = "processId") String processId) {
        try{
//            runtimeService.setVariable(processId, "validationDone", true);
        }catch(Exception e){
            return "Error happened... " + e.getMessage();
        }
        return "Variable updated";
    }


    @GetMapping("/retry/{processId}")
    public String retry(@PathVariable(name = "processId") String processId) {
//        ProcessInstanceModificationBuilder processInstanceModification = runtimeService.createProcessInstanceModification(processId);
//
//        processInstanceModification.cancelActivityInstance(processId);
//        processInstanceModification.startBeforeActivity("stuck_flow_one");
//
//        processInstanceModification.execute();

        return "process retried";
    }
}
