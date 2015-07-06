package jp.mzw.revajaxmutator.test.tagged_gallery;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import jp.gr.java_conf.daisy.ajax_mutator.MutateVisitor;
import jp.gr.java_conf.daisy.ajax_mutator.MutateVisitorBuilder;
import jp.gr.java_conf.daisy.ajax_mutator.MutationTestConductor;
import jp.gr.java_conf.daisy.ajax_mutator.detector.AbstractDetector;
import jp.gr.java_conf.daisy.ajax_mutator.detector.dom_manipulation_detector.DOMSelectionDetector;
import jp.gr.java_conf.daisy.ajax_mutator.mutatable.DOMSelection;
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
import jp.mzw.revajaxmutator.MutateConfigurationBase;
import jp.mzw.revajaxmutator.fixer.DOMSelectionSelectGivenFixer;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

import com.google.common.collect.ImmutableSet;

public class TaggedGalleryConfig {
	
	private static String getJsFilepath() throws IOException {
		Properties config = WebAppTestBase.getConfig("tagged-gallery.properties");
		String dir = config.getProperty("ram_record_dir");
		String jsfile = config.getProperty("ram_mutate_jsfile_url");

		File file = new File(dir, URLEncoder.encode(jsfile, "utf-8"));
		return file.getAbsolutePath();
	}

    public static class FixConfiguration extends MutateConfigurationBase {
        @SuppressWarnings("rawtypes")
        public FixConfiguration() throws IOException {
            MutateVisitorBuilder builder = MutateVisitor.defaultJqueryBuilder();
            builder.setDomSelectionDetectors(ImmutableSet.<AbstractDetector<DOMSelection>>of(
                    new DOMSelectionDetector()));
            visitor = builder.build();

            conductor = new MutationTestConductor();
            conductor.setup(getJsFilepath(), "", visitor);

            mutators = ImmutableSet.<Mutator> of(
                    new DOMSelectionSelectGivenFixer("\"tg-resizecrop\""));
        }
    }

    public static class MutateConfiguration extends MutateConfigurationBase {
        @SuppressWarnings("rawtypes")
        public MutateConfiguration() throws IOException {
            MutateVisitorBuilder builder = MutateVisitor.defaultJqueryBuilder();
            builder.setDomSelectionDetectors(ImmutableSet.<AbstractDetector<DOMSelection>>of(
                    new DOMSelectionDetector()));
            visitor = builder.build();

            conductor = new MutationTestConductor();
            conductor.setup(getJsFilepath(), "", visitor);

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
	                new RequestUrlRAMutator(visitor.getRequests())
            		);
        }
    }
}
