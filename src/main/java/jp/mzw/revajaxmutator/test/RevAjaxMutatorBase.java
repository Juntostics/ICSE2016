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
	private static Proxy mProxy;
	private static List<ProxyPlugin> mPlugins;
	private static String mPort;
	
	public static void launchProxyServer(List<ProxyPlugin> plugins, String port) throws StoreException, InterruptedException {
		mPlugins = plugins;
		mPort = port;
		
        Framework framework = new Framework();
        Preferences.setPreference("Proxy.listeners", "127.0.0.1:" + port);
        framework.setSession("FileSystem", new File(".conversation"), "");
        
        mProxy = new Proxy(framework);
        for(ProxyPlugin plugin : plugins) {
        	mProxy.addPlugin(plugin);
        }
		
		mProxy.run();
    	Thread.sleep(300); // wait for launching proxy server
	}

    public static void relaunchProxyServerWith(FilterPlugin filter_plugin) throws InterruptedException, StoreException {
    	if(mProxy.stop()) {
//    		mPlugins.add(filter_plugin);
//            for(ProxyPlugin plugin : mPlugins) {
//            	mProxy.addPlugin(plugin);
//            }
    		mProxy.addPlugin(filter_plugin);
    		System.out.println(mProxy.getPlugin("FilterPlugin"));
    		mProxy.run();
    		Thread.sleep(300); // wait for launching proxy server
    	}
    }
	
	
    public static void interruptProxyServer() {
    	if(mProxy != null) {
    		mProxy.stop();
    	}
    }
}
