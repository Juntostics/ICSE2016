package jp.mzw.revajaxmutator.test.themes_plus;

import jp.gr.java_conf.daisy.ajax_mutator.MutateVisitor;
import jp.gr.java_conf.daisy.ajax_mutator.MutateVisitorBuilder;
import jp.gr.java_conf.daisy.ajax_mutator.MutationTestConductor;
import jp.gr.java_conf.daisy.ajax_mutator.detector.EventAttacherDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.AppendChildDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.AttributeAssignmentDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.CloneNodeDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.CreateElementDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.DOMNormalizationDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.DOMSelectionDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.RemoveChildDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.ReplaceChildDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.SetAttributeDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.event_detector.AddEventListenerDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.event_detector.AttachEventDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.event_detector.TimerEventDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryAppendDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryAttributeModificationDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryCloneDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryDOMSelectionDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryEventAttachmentDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryRemoveDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryReplaceWithDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.jquery.JQueryRequestDetector;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.DOMSelectionSelectNearbyMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.Mutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.AppendedDOMRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.AttributeModificationTargetRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.AttributeModificationValueRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.EventCallbackRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.EventTargetRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.EventTypeRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.RequestMethodRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.RequestOnSuccessHandlerRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.RequestUrlRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.TimerEventCallbackRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.replacing_among.TimerEventDurationRAMutator;
import jp.gr.java_conf.daisy.ajax_mutator.mutator.DOMSelectionSelectNearbyMutator;
import jp.mzw.revajaxmutator.fixer.DOMSelectionSelectGivenFixer;
import jp.mzw.revajaxmutator.MutateConfigurationBase;
import jp.mzw.revajaxmutator.mutator.JQueryAsyncCommMethodMutator;

import com.google.common.collect.ImmutableSet;

public class ThemesPlusConfig {
	
	public static final String PATH_TO_JS_FILE = "record/themesplus/http%3A%2F%2Flocalhost%3A80%2Fwordpress%2Fwp-content%2Fplugins%2Fthemes-plus%2Fjs%2Fcountto.min.js%3Fver%3D1.0";
	//./ram.sh mutate   jp.mzw.revajaxmutator.test.themes_plus.ThemesPlusConfig\$MutateConfiguration
	//./ram.sh analysis jp.mzw.revajaxmutator.test.themes_plus.ThemesPlusConfig\$MutateConfiguration     jp.mzw.revajaxmutator.test.themes_plus.ThemesPlusTest
//	public static class FixConfiguration extends MutateConfigurationBase {
//	    @SuppressWarnings("rawtypes")
//		public FixConfiguration() {
//	        MutateVisitorBuilder builder = MutateVisitor.defaultJqueryBuilder();
//	        builder.setRequestDetectors(ImmutableSet.of(new JQueryRequestDetector()));
//	        builder.setEventAttacherDetectors(ImmutableSet.<EventAttacherDetector>of(new JQueryEventAttachmentDetector()));
//	        visitor = builder.build();
//	
//	        conductor = new MutationTestConductor();
//	        conductor.setup(PATH_TO_JS_FILE, "", visitor);
//	
//	        mutators = ImmutableSet.<Mutator>of(
//	        		new JQueryAsyncCommMethodMutator()
////	        		new EventTargetRAMutator(visitor.getEventAttachments()),
////	                new EventTypeRAMutator(visitor.getEventAttachments()),
////	                new EventCallbackRAMutator(visitor.getEventAttachments()),
////	                new TimerEventDurationRAMutator(visitor.getTimerEventAttachmentExpressions()),
////	                new TimerEventCallbackRAMutator(visitor.getTimerEventAttachmentExpressions()),
////	                new AppendedDOMRAMutator(visitor.getDomAppendings()),
////	                new AttributeModificationTargetRAMutator(visitor.getAttributeModifications()),
////	                new AttributeModificationValueRAMutator(visitor.getAttributeModifications()),
////	                new DOMSelectionSelectNearbyMutator(),
////	                new RequestOnSuccessHandlerRAMutator(visitor.getRequests()),
////	                new RequestMethodRAMutator(visitor.getRequests()),
////	                new RequestUrlRAMutator(visitor.getRequests())
//	        		);
//	    }
//	}
	public static class MutateConfiguration extends MutateConfigurationBase {
	    @SuppressWarnings("rawtypes")
		public MutateConfiguration() {
	    	
            MutateVisitorBuilder builder = MutateVisitor.emptyBuilder();
            builder.setAttributeModificationDetectors(
                    ImmutableSet.of(
                                    new AttributeAssignmentDetector(),
                                    new SetAttributeDetector(),
                                    new JQueryAttributeModificationDetector()));
            builder.setDomAppendingDetectors(
                    ImmutableSet.of(
                                    new AppendChildDetector(),
                                    new JQueryAppendDetector()));
            builder.setDomCreationDetectors(
                    ImmutableSet.of(
                                    new CreateElementDetector()));
            builder.setDomCloningDetectors(
                    ImmutableSet.of(
                                    new CloneNodeDetector(),
                                    new JQueryCloneDetector()));
            builder.setDomNormalizationDetectors(
                    ImmutableSet.of(
                                    new DOMNormalizationDetector()));
            builder.setDomReplacementDetectors(
                    ImmutableSet.of(
                                    new ReplaceChildDetector(),
                                    new JQueryReplaceWithDetector()));
            builder.setDomRemovalDetectors(
                    ImmutableSet.of(
                                    new RemoveChildDetector(),
                                    new JQueryRemoveDetector()));
            builder.setDomSelectionDetectors(
                    ImmutableSet.of(
                                    new DOMSelectionDetector(),
                                    new JQueryDOMSelectionDetector()));
            builder.setEventAttacherDetectors(
                    ImmutableSet.<EventAttacherDetector>of(
                                    new AddEventListenerDetector(),
                                    new AttachEventDetector(),
                                    new JQueryEventAttachmentDetector()));
            builder.setTimerEventDetectors(
                    ImmutableSet.of(
                                    new TimerEventDetector()));
            builder.setRequestDetectors(
                    ImmutableSet.of(
                                    new JQueryRequestDetector()));
            
	        visitor = builder.build();
	        conductor = new MutationTestConductor();
	        conductor.setup(PATH_TO_JS_FILE, "", visitor);

            mutators = ImmutableSet.<Mutator>of(
                    new EventTargetRAMutator(visitor.getEventAttachments()),
                    new EventTypeRAMutator(visitor.getEventAttachments()),
                    new EventCallbackRAMutator(visitor.getEventAttachments()),
                    new TimerEventDurationRAMutator(visitor.getTimerEventAttachmentExpressions()),
                    new TimerEventCallbackRAMutator(visitor.getTimerEventAttachmentExpressions()),
                    new RequestUrlRAMutator(visitor.getRequests()),
                    new RequestOnSuccessHandlerRAMutator(visitor.getRequests()),
                    new DOMSelectionSelectNearbyMutator(),
                    new AttributeModificationTargetRAMutator(visitor.getAttributeModifications()),
                    new AttributeModificationValueRAMutator(visitor.getAttributeModifications())
                    );
	        
//            mutators = ImmutableSet.<Mutator> of(
//                    new DOMSelectionSelectGivenFixer("\"#timer\""));
	    }
	}
}
