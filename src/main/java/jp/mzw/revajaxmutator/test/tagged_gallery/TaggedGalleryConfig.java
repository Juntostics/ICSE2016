package jp.mzw.revajaxmutator.test.tagged_gallery;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Set;

import jp.gr.java_conf.daisy.ajax_mutator.MutateVisitor;
import jp.gr.java_conf.daisy.ajax_mutator.MutateVisitorBuilder;
import jp.gr.java_conf.daisy.ajax_mutator.MutationTestConductor;
import jp.gr.java_conf.daisy.ajax_mutator.detector.AbstractDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.EventAttacherDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.*;
import jp.gr.java_conf.daisy.ajax_mutator.detector.event_detector.*;
import jp.gr.java_conf.daisy.ajax_mutator.mutatable.*;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.DOMSelectionSelectNearbyMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.Mutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.*;
import jp.mzw.revajaxmutator.MutateConfigurationBase;
import jp.mzw.revajaxmutator.fixer.DOMSelectionSelectGivenFixer;
import jp.mzw.revajaxmutator.test.WebAppTestBase;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.*;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_to_no_op.*;

import com.google.common.collect.ImmutableSet;

public class TaggedGalleryConfig {
	public static final String PATH_TO_JS_FILE = "record/tagged-gallery/http%3A%2F%2F192.168.59.103%3A80%2Fwp-content%2Fplugins%2Ftagged-gallery%2Fjs%2Fresize.js%3Fver%3D3.3.3";
	
	private static String getJsFilepath() throws IOException {
		Properties config = WebAppTestBase.getConfig("tagged-gallery.properties");
		String dir = config.getProperty("ram_record_dir");
		String jsfile = config.getProperty("ram_mutate_jsfile_url");

		File file = new File(dir, URLEncoder.encode(jsfile, "utf-8"));
		return file.getAbsolutePath();
	}

//    public static class FixConfiguration extends MutateConfigurationBase {
//        @SuppressWarnings("rawtypes")
//        public FixConfiguration() throws IOException {
//            MutateVisitorBuilder builder = MutateVisitor.defaultJqueryBuilder();
//            builder.setDomSelectionDetectors(ImmutableSet.<AbstractDetector<DOMSelection>>of(
//                    new DOMSelectionDetector()));
//            visitor = builder.build();
//
//            conductor = new MutationTestConductor();
//            conductor.setup(getJsFilepath(), "", visitor);
//
//            mutators = ImmutableSet.<Mutator> of(
//                    new DOMSelectionSelectGivenFixer("\"tg-resizecrop\""));
//        }
//    }

    public static class MutateConfiguration extends MutateConfigurationBase {
        @SuppressWarnings("rawtypes")
        public MutateConfiguration() throws IOException {
            MutateVisitorBuilder builder = MutateVisitor.defaultJqueryBuilder();

            builder.setEventAttacherDetectors(ImmutableSet.<EventAttacherDetector>of(
            		new AttachEventDetector()));
            
            builder.setTimerEventDetectors(ImmutableSet.<TimerEventDetector>of(
            		new TimerEventDetector()));
            
            builder.setDomSelectionDetectors(ImmutableSet.<AbstractDetector<DOMSelection>>of(
                    new DOMSelectionDetector()));
                        
            builder.setDomNormalizationDetectors(ImmutableSet.<AbstractDetector<DOMNormalization>>of(
            		new DOMNormalizationDetector()));
            
            builder.setAttributeModificationDetectors(ImmutableSet.<AbstractDetector<AttributeModification>>of(
            		new AttributeAssignmentDetector()));

            builder.setAttributeModificationDetectors(ImmutableSet.<AbstractDetector<AttributeModification>>of(
            		new SetAttributeDetector()));
            
            builder.setDomCreationDetectors(ImmutableSet.<AbstractDetector<DOMCreation>>of(
            		new CreateElementDetector()));
            
            builder.setDomRemovalDetectors(ImmutableSet.<AbstractDetector<DOMRemoval>>of(
            		new RemoveChildDetector()));
            
            builder.setDomAppendingDetectors(ImmutableSet.<AbstractDetector<DOMAppending>>of(
            		new AppendChildDetector()));

            builder.setDomReplacementDetectors(ImmutableSet.<AbstractDetector<DOMReplacement>>of(
            		new ReplaceChildDetector()));
            
            builder.setDomCloningDetectors(ImmutableSet.<AbstractDetector<DOMCloning>>of(
            		new CloneNodeDetector()));
            
            visitor = builder.build();

            conductor = new MutationTestConductor();
            conductor.setup(PATH_TO_JS_FILE, "", visitor);

            mutators = ImmutableSet.<Mutator>of(
	        		new EventTargetRAMutator(visitor.getEventAttachments()),
	                new EventTypeRAMutator(visitor.getEventAttachments()),
	                new EventCallbackRAMutator(visitor.getEventAttachments()),
	                new TimerEventDurationRAMutator(visitor.getTimerEventAttachmentExpressions()),
	                new TimerEventCallbackRAMutator(visitor.getTimerEventAttachmentExpressions()),
	                new AppendedDOMRAMutator(visitor.getDomAppendings()),
	                new AttributeModificationTargetRAMutator(visitor.getAttributeModifications()),
	                new AttributeModificationValueRAMutator(visitor.getAttributeModifications()),
	                new DOMSelectionSelectNearbyMutator(),
	                new RequestOnSuccessHandlerRAMutator(visitor.getRequests()),
	                new RequestMethodRAMutator(visitor.getRequests()),
	                new RequestUrlRAMutator(visitor.getRequests()),
	        		new FakeBlankResponseBodyMutator(),
	        		new DOMCreationToNoOpMutator(),
	        		new DOMRemovalToNoOpMutator(),
	        		new DOMReplacementSrcTargetMutator(), 
	        		new DOMCloningToNoOpMutator(),
	        		new DOMNormalizationToNoOpMutator()	        		
            		);
        }
    }
}