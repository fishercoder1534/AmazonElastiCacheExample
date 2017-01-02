import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticache.AmazonElastiCacheClient;
import com.amazonaws.services.elasticache.model.*;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by stevesun on 12/18/16.
 */
public class AmazonElastiCacheExample {
    public static void main(String[] args) {
        System.out.println("Entered.");

//		Jedis jedisTest = new Jedis("localhost");
//		jedisTest.set("foo", "bar");
//		String value = jedisTest.get("foo");
//		System.out.println("value = " + value);

        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("search-dev").getCredentials();
            System.out.println("Went thru credentials.");
        } catch (Exception e) {
            System.out.println("Got exception..........");
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Users/USERNAME/.aws/credentials), and is in valid format.", e);
        }

        AmazonElastiCacheClient client = new AmazonElastiCacheClient(credentials);
        System.out.println("Access Key: " + credentials.getAWSAccessKeyId());
        System.out.println("Secret Key: " + credentials.getAWSSecretKey());
        System.out.println("Got client, client.getEndpointPrefix() = " + client.getEndpointPrefix());
        client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
//		client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
//		client.setEndpoint("https://hermes-dev-0001-001.nquffl.0001.apn2.cache.amazonaws.com:6379");
        System.out.println("setEndpoint passed.");
        DescribeCacheClustersRequest dccRequest = new DescribeCacheClustersRequest();
        dccRequest.setShowCacheNodeInfo(true);

        System.out.println("About to call describeCacheClusters() now");
        DescribeCacheClustersResult clusterResult = client.describeCacheClusters(dccRequest);
        System.out.println("got clusterResult.");

        System.out.println("CacheEngineVersions: " + client.describeCacheEngineVersions());

        List<CacheCluster> cacheClusters = clusterResult.getCacheClusters();
        System.out.println("About to enter for loop now, cacheClusters.size() = " + cacheClusters.size());
        for (CacheCluster cacheCluster : cacheClusters) {
            System.out.println("In for loop now.");
            for (CacheNode cacheNode : cacheCluster.getCacheNodes()) {
                System.out.println("In inner for loop now.");
                System.out.println(cacheNode.toString());
                String addr = cacheNode.getEndpoint().getAddress();
//				if (!addr.startsWith("hermes-dev")) continue;
                int port = cacheNode.getEndpoint().getPort();
                String url =  addr + ":" + port;
                System.out.println("formed url is: " + url);

                Jedis jedis = new Jedis(url);
                System.out.println("Connection to server sucessfully");
                // check whether server is running or not
                System.out.println("Server is running: " + jedis.ping());
//				System.out.println("Server is running: " + jedis.clusterInfo());
            }
        }

    }
}
