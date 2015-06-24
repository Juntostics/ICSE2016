package jp.mzw.revajaxmutator.test;

import java.io.File;

import org.owasp.webscarab.model.Preferences;
import org.owasp.webscarab.model.StoreException;
import org.owasp.webscarab.plugin.Framework;
import org.owasp.webscarab.plugin.proxy.Proxy;
import org.owasp.webscarab.plugin.proxy.ProxyPlugin;

public class RevAjaxMutatorBase {
	private static Thread server;

	public static void launchProxyServer(ProxyPlugin plugin, String port) throws StoreException, InterruptedException {
        Framework framework = new Framework();
        Preferences.setPreference("Proxy.listeners", "127.0.0.1:" + port);
        framework.setSession("FileSystem", new File(".conversation"), "");
        
        Proxy proxy = new Proxy(framework);
        proxy.addPlugin(plugin);

		if(server == null) {
			server = new Thread(proxy);
			server.start();
	    	Thread.sleep(300); // wait for launching proxy server
		}
	}
	
    public static void interruptProxyServer() {
    	if(server == null) {
    		return;
    	}
    	server.interrupt();
    }
}
