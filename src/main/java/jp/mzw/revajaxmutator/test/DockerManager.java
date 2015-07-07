package jp.mzw.revajaxmutator.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workaround
 * @author yuta
 *
 */
public class DockerManager {
	private static Logger log = LoggerFactory.getLogger(DockerManager.class);
	
	static String DOCKER = "/usr/local/bin/docker";
	static String BASH = "/bin/bash";
	static String MYSQL = "/usr/local/bin/mysql";
	static String MYSQL_MYCONF = "docker/my.conf";
	
	static String BOOT2DOCKER_IP = "192.168.59.103";
	static String[] DOCKER_ENV = {
			"DOCKER_HOST=tcp://"+BOOT2DOCKER_IP+":2376",
			"DOCKER_CERT_PATH=/Users/yuta/.boot2docker/certs/boot2docker-vm",
			"DOCKER_TLS_VERIFY=1"
			};
	
	public static void main(String[] args) throws IOException {
		runContainer4ThemesPlusTest();
	}
	
	public static void runContainer4ThemesPlusTest() throws IOException {
		String dumpfile = "docker/themes-plus/wp-dbbase.dump.sql";
		
		cleanContainer();
		
		// Precondition: $ docker build -t mysql docker/mysql/5.5/
		log.info("Instantiating MySQL container");
		String result_mysql_run = exec(new String[]{DOCKER, "run", "-d", "-p", "3306:3306",
				"-e", "MYSQL_PASS=mypass", "--name=mysql.tp", "mysql",
				},
				DOCKER_ENV);
		log.info(result_mysql_run);
		
		// Workaround: wait for launching mysql server
		try {
			Thread.sleep(3000); // heuristic
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("Loading dump file to MySQL container");
		String reuslt_mysql_load_dump = exec(new String[]{
				BASH, "-c",
				MYSQL + " --defaults-extra-file=" + MYSQL_MYCONF + " --verbose < " + dumpfile,
				}, null);
		log.info(reuslt_mysql_load_dump);
		
		// Precondition: $ docker build -t yuta/bs --rm=true docker/themes-plus/
		log.info("Instantiating WordPress container");
		String result_wp_run = exec(new String[]{DOCKER, "run", "-d", "-p", "80:80", "-p", "10022:22",
				"--name=wp.tp", "yuta/tp"},
				DOCKER_ENV);
		log.info(result_wp_run);
	}
	
	public static void runContainer4WpPluginTest(String dumpfile) throws IOException {
		cleanContainer();
		
		// Precondition: $ docker build -t mysql docker/mysql/5.5/
		log.info("Instantiating MySQL container");
		String result_mysql_run = exec(new String[]{DOCKER, "run", "-d", "-p", "3306:3306",
				"-e", "MYSQL_PASS=mypass", "--name=mysql.tp", "mysql",
				},
				DOCKER_ENV);
		log.info(result_mysql_run);
		
		// Workaround: wait for launching mysql server
		try {
			Thread.sleep(3000); // heuristic
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("Loading dump file to MySQL container");
		String reuslt_mysql_load_dump = exec(new String[]{
				BASH, "-c",
				MYSQL + " --defaults-extra-file=" + MYSQL_MYCONF + " --verbose < " + dumpfile,
				}, null);
		log.info(reuslt_mysql_load_dump);
		
		// Precondition: $ docker build -t yuta/bs --rm=true docker/themes-plus/
		log.info("Instantiating WordPress container");
		String result_wp_run = exec(new String[]{DOCKER, "run", "-d", "-p", "80:80", "-p", "10022:22",
				"--name=wp.tp", "yuta/tp"},
				DOCKER_ENV);
		log.info(result_wp_run);
	}
	
	public static String exec(String[] cmd, String[] env) throws IOException {
		Process proc = Runtime.getRuntime().exec(cmd, env);

		StringBuilder ret = new StringBuilder();
		String delim = "";
		try {
			InputStream is = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = br.readLine()) != null) {
				ret.append(delim).append(line);
				delim = "\n";
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		proc.destroy();
		
		return ret.toString();
	}
	
	public static List<DockerContainer> parse() throws IOException {
		ArrayList<DockerContainer> ret = new ArrayList<DockerContainer>();
		
		String result = exec(new String[]{DOCKER, "ps", "-a"}, DOCKER_ENV);

		int index_id = 0;
		int index_image = 0;
		int index_command = 0;
		int index_created = 0;
		int index_status = 0;
		int index_ports = 0;
		int index_names = 0;
		for(String line : result.split("\n")) {
			if(line.startsWith("CONTAINER ID")) {
				index_id = line.indexOf("CONTAINER ID");
				index_image = line.indexOf("IMAGE");
				index_command = line.indexOf("COMMAND");
				index_created = line.indexOf("CREATED");
				index_status = line.indexOf("STATUS");
				index_ports = line.indexOf("PORTS");
				index_names = line.indexOf("NAMES");
			} else {
				String id = line.substring(index_id, index_image).trim();
				String image = line.substring(index_image, index_command).trim();
				String command = line.substring(index_command, index_created).trim();
				String created = line.substring(index_created, index_status).trim();
				String status = line.substring(index_status, index_ports).trim();
				String ports = line.substring(index_ports, index_names).trim();
				String names = line.substring(index_names).trim();
				
				DockerContainer c = new DockerContainer(id, image, command, created, status, ports, names);
				ret.add(c);
			}
		}
		return ret;
	}
	
	public static void cleanContainer() throws IOException {
		log.info("Cleaning Docker containers...");
		List<DockerContainer> containerList = parse();
		for(DockerContainer c : containerList) {
			if(c.isUp()) {
				log.info("Stopping container: " + c.getId() + ", " + c.getNames());
				String result = exec(new String[]{DOCKER, "stop", c.getId()}, DOCKER_ENV);
				log.info(result);
			}
			log.info("Removing container: " + c.getId() + ", " + c.getNames());
			String result = exec(new String[]{DOCKER, "rm", c.getId()}, DOCKER_ENV);
			log.info(result);
		}
	}

	static class DockerContainer {
		String cId;
		String cImage;
		String cCommand;
		String cCreated;
		String cStatus;
		String cPorts;
		String cNames;
		DockerContainer(String id, String image, String command, String created, String status, String ports, String names) {
			cId = id;
			cImage = image;
			cCommand = command;
			cCreated = created;
			cStatus = status;
			cPorts = ports;
			cNames = names;
		}
		
		public String toString() {
			return cId + ", " + cImage + ", " + cCommand + ", " + cCreated + ", " + cStatus + ", " + cPorts + ", " + cNames;
		}

		public String getId() {
			return cId;
		}
		public String getNames() {
			return cNames;
		}
		public boolean isUp() {
			return cStatus.startsWith("Up");
		}
		public boolean isExited() {
			return cStatus.startsWith("Exited");
		}
	}
}
