package jp.mzw.revajaxmutator.test;

import java.io.File;
import java.util.List;

import jp.mzw.revajaxmutator.FilterPlugin;

import org.owasp.webscarab.model.Preferences;
import org.owasp.webscarab.model.StoreException;
import org.owasp.webscarab.plugin.Framework;
import org.owasp.webscarab.plugin.proxy.Proxy;
import org.owasp.webscarab.plugin.proxy.ProxyPlugin;

public class RevAjaxMutatorBase {
	private static Proxy proxy;
	
	public static void launchProxyServer(List<ProxyPlugin> plugins, String port) throws StoreException, InterruptedException {
        Framework framework = new Framework();
        Preferences.setPreference("Proxy.listeners", "127.0.0.1:" + port);
        framework.setSession("FileSystem", new File(".conversation"), "");
        
        Proxy proxy = new Proxy(framework);
        for(ProxyPlugin plugin : plugins) {
        	proxy.addPlugin(plugin);
        }
		
		proxy.run();
    	Thread.sleep(300); // wait for launching proxy server
	}

    public static void relaunchProxyServerWith(FilterPlugin plugin) throws InterruptedException {
//    	proxy.stop();
    	proxy.addPlugin(plugin);
//		proxy.run();
    	Thread.sleep(300); // wait for launching proxy server
    }
	
	
    public static void interruptProxyServer() {
    	if(proxy != null) {
    		proxy.stop();
    	}
    }
}
