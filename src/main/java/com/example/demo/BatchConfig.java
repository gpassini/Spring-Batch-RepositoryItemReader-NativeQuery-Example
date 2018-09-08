package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory steps;
	
	@Bean
	public Job myJob(Step myStep) {
		return jobs.get("myJob")
				.flow(myStep)
				.end()
				.build();
	}

	@Bean
	public Step myStep(RepositoryItemReader<MyItem> myReader, ItemProcessor<MyItem, MyItem> myProcessor) {
		return steps.get("myStep")
				.<MyItem, MyItem>chunk(5)
				.reader(myReader)
				.processor(myProcessor)
				.build();
	}
	
	@Bean
	public RepositoryItemReader<MyItem> myReader(MyItemRepository repository) {
		
		Map<String, Direction> sortMap = new HashMap<>();
		sortMap.put("id", Direction.DESC);
		
		return new RepositoryItemReaderBuilder<MyItem>()
				.repository(repository)
				.methodName("findTempByStatus")
				.arguments(Arrays.asList(1L, 2L))
				.sorts(sortMap)
				.saveState(false)
				.build();
	}
	
	@Bean
	public ItemProcessor<MyItem, MyItem> myProcessor() {
		return it -> {
			System.out.println(it);
			return it;
		};
	}
}
