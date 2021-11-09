package com.redhat.kafkasizing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.redhat.kafkasizing.model.SizingParams;
import com.redhat.kafkasizing.model.SizingResults;
import com.redhat.kafkasizing.service.SizingService;


@SpringBootTest
public class ApplicationTest {
	@Autowired
	KafkaSizingController controller;


	@Test
	public void controllerTest() {

		Assertions.assertThat(controller).isNotEqualTo(null);
	}
	
	@Test
	public void serviceTest() {
		SizingService service = controller.getService();
		System.out.println("service: " + service);
		
		SizingParams parms = new SizingParams();
		parms.setConsumerGroups(2);
		parms.setDiskThroughput(125);
		parms.setLaggingConsumers(0);
		parms.setMaxUtil((float) 0.65);
		parms.setMessageRate(2000);
		parms.setMessageSize(2000);
		parms.setNetSpeed((float) 1.0);
		parms.setReplicas(3);
		parms.setRetentionPariod(7);
		parms.setZkFailures(1);
		
		SizingResults results = service.sizeKafkaCluster(parms);
		//System.out.println("results: " + results.toString());
		
		/*
		results: SizingResults [
			dailyDiskUsage=1037,
			totalDiskStorage=7259,
			zkNodes=3,
			brokerNodes=4,
			cores=16,
			diskPerBroker=1997,
			memPerBroker=32,
			vcpusPerZkNodes=4,
			memPerZkNode=16,
			vcpusPerBroker=8,
			diskPerZkNode=100,
			topicThroughput=0,
			partitions=0
		 */

		Assertions.assertThat(results.getDailyDiskUsage()).isEqualTo(1037);
		Assertions.assertThat(results.getBrokerNodes()).isEqualTo(4);
	}
}
