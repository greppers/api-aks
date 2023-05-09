package tn.esprit.pidev.Util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@Configuration
public class ObjectStorage {

	// @Value("DO00VLM3WGWHRR3LA2U6")
	private String doSpaceKey="DO00HDJRK6N7W8P2BWJW";

	// @Value("dSkgpu6iu1BhAM+xewcIto76Javivh22PomtXr8FQlQ")
	private String doSpaceSecret="FzSm7GfMRUg2fY4zUtNqbZhZiwFkKyBOS7yYm22Qzpo";

	// @Value("${do.space.endpoint}")
	private String doSpaceEndpoint="https://fra1.digitaloceanspaces.com";

	// @Value("${do.space.region}")
	private String doSpaceRegion="frankfurt-1";

	@Bean
	public AmazonS3 getS3() {
		BasicAWSCredentials creds = new BasicAWSCredentials(doSpaceKey,doSpaceSecret );
		return AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(doSpaceEndpoint, doSpaceRegion))
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
	}

}
