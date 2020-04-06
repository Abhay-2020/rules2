package com.sapient.rulesengine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.model.AssignRules;
import com.sapient.model.ContestBean;
import com.sapient.model.HackerDataFetch;
import com.sapient.model.LeaderBoardBean;

@SpringBootTest
class RulesEngineApplicationTests {

	private JSONObject activityData = new JSONObject();
	private ObjectMapper mapper = new ObjectMapper();
	
	
	@Autowired
	RulesEngineController rec ;
	
	
@Test
public void controller_loads()
{
	String hello = rec.HelloWorld();
	assertEquals("hello world",hello);
}


@Test
public void controller_load_fails()
{
	String hello = rec.HelloWorld();
	assertEquals(null,null);
}


@Test
public void get_activity_data_works() throws JsonParseException, JsonMappingException, IOException
{
	
	List<JSONObject> m = mapper.readValue(new File(
            "C:\\Rules\\datawithudemy.json"), new TypeReference<List<JSONObject>>() {
    }); 
	assertEquals(6, rec.getActivityData(m).size());
}

@Test
public void topFive_works_when_user_is_in_top_five() throws JsonParseException, JsonMappingException, IOException
{
	List<JSONObject> m = mapper.readValue(new File(
            "C:\\Rules\\top5not.json"), new TypeReference<List<JSONObject>>() {
    });
	
	assertEquals(5, rec.topFive("abhay@abcd.com",m).size());
}

@Test
public void leaderPOJOget()
{
	LeaderBoardBean bean = new LeaderBoardBean();
	
	assertEquals("",bean.getName());
}
@Test
public void leaderPOJOset()
{
	LeaderBoardBean bean = new LeaderBoardBean();
	bean.setName("abhay");
	assertEquals("abhay",bean.getName());
}


@Test
public void leaderPOJO_NoArg()
{
	LeaderBoardBean bean = new LeaderBoardBean();
	
	assertEquals("",bean.getName());
}

@Test
public void leaderPOJO_ALL_arg()
{
	LeaderBoardBean bean = new LeaderBoardBean(1,"abhay@h.com","abhay",20,null,20.0);
	assertEquals("abhay",bean.getName());
}

@Test
public void assignPOJOALL_arg()
{
	AssignRules asr = new AssignRules("TOP_80",true);
	
	assertEquals(true,asr.isStatus());
}

@Test
public void test_comparator_completely() throws JsonParseException, JsonMappingException, IOException
{
	List<JSONObject> m = mapper.readValue(new File(
            "C:\\Rules\\top5not.json"), new TypeReference<List<JSONObject>>() {
    });
	
	assertEquals(5, rec.topFive("abhay@abcd.com",m).size());
}

@Test
public void topFive_works_when_user_is_not_in_top_five() throws JsonParseException, JsonMappingException, IOException
{
	List<JSONObject> m = mapper.readValue(new File(
            "C:\\Rules\\top5not.json"), new TypeReference<List<JSONObject>>() {
    });
	
	assertEquals(6, rec.topFive("dhoni@abcd.com",m).size());
}

@Test
public void topFive_doesnt_works() throws JsonParseException, JsonMappingException, IOException
{
	List<JSONObject> m = mapper.readValue(new File(
            "C:\\Rules\\top5not.json"), new TypeReference<List<JSONObject>>() {
    });
	
	assertEquals(0, rec.topFive("adhoni@abcd.com",m).size());
}

@Test
public void test_for_contest_bean_get_name()
{
	ContestBean cb = new ContestBean();
	
	assertEquals("",cb.getContestname());
}

@Test
public void test_for_contest_bean_get_score()
{
	ContestBean cb = new ContestBean();
	Integer cs = cb.getContestscore();
	assertEquals((Integer)0,cs);
	
}

@Test
public void hacker_get_score() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getActivityScore());
}

@Test
public void hacker_get_batch() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getBatch());
}
@Test
public void hacker_get_batch_category() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getCategory());
}

@Test
public void leader_get_name()
{
	assertEquals("",new LeaderBoardBean().getName());
}
@Test
public void leader_get_rank()
{
	assertEquals((Integer)0,new LeaderBoardBean().getRank());
}
@Test
public void leader_get_totalScore()
{
	assertEquals((Integer)0,new LeaderBoardBean().getTotalscore());
}
@Test
public void leader_get_udemyProgress()
{
	assertEquals((Double)0.0,new LeaderBoardBean().getUdemyProgress());
}
@Test
public void leader_get_email()
{
	assertEquals("",new LeaderBoardBean().getEmailid());
}

@Test
public void hacker_get_batch_email() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getEmailId());
}

@Test
public void hacker_get_batch_name() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getName());
}
@Test
public void hacker_get_batch_score() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getScore());
}

@Test
public void hacker_get_batch_stime() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getStartDateTime());
}
@Test
public void hacker_get_batch_etime() {
	HackerDataFetch hdf = new HackerDataFetch();
	
	assertEquals(null,hdf.getEndDateTime());
}




@Test
public void test_main_application()
{
	RulesEngineApplication.main(new String[] {});
}



}
