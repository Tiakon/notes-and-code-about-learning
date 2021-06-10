package cn.tiakon.learn.java.core.optionsparse;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

public class JoptSimpleDemo {

    public static void main(String[] args) throws IOException {

        OptionParser parser = new OptionParser();
        ArgumentAcceptingOptionSpec<String>  brokerListOpt  = parser.accepts("broker-list", "REQUIRED: The list of hostname and port of the server to connect to.")
                .withRequiredArg()
                .describedAs("hostname:port,...,hostname:port")
                .ofType(String.class);

        ArgumentAcceptingOptionSpec<String> topicOpt = parser.accepts("topic", "REQUIRED: The topic to get offset from.")
                .withRequiredArg()
                .describedAs("topic")
                .ofType(String.class);

        ArgumentAcceptingOptionSpec<String> partitionOpt = parser.accepts("partitions", "comma separated list of partition ids. If not specified, it will find offsets for all partitions")
                .withRequiredArg()
                .describedAs("partition ids")
                .ofType(String.class)
                .defaultsTo("");

        ArgumentAcceptingOptionSpec<Long> timeOpt = parser.accepts("time", "timestamp of the offsets before that")
                .withRequiredArg()
                .describedAs("timestamp/-1(latest)/-2(earliest)")
                .ofType(Long.class)
                .defaultsTo(-1L);

        ArgumentAcceptingOptionSpec<Integer> nOffsetsOpt = parser.accepts("offsets", "number of offsets returned")
                .withRequiredArg()
                .describedAs("count")
                .ofType(Integer.class)
                .defaultsTo(1);

        ArgumentAcceptingOptionSpec<Integer> maxWaitMsOpt = parser.accepts("max-wait-ms", "The max amount of time each fetch request waits.")
                .withRequiredArg()
                .describedAs("ms")
                .ofType(Integer.class)
                .defaultsTo(1000);

        if(args.length == 0)
            printUsageAndDie(parser, "An interactive shell for getting consumer offsets.");

        OptionSet parse = parser.parse(args);

        System.out.println(parse.valueOf(brokerListOpt));
        System.out.println(parse.valueOf(topicOpt));
        System.out.println(parse.valueOf(partitionOpt));
        System.out.println(parse.valueOf(timeOpt));
        System.out.println(parse.valueOf(nOffsetsOpt));
        System.out.println(parse.valueOf(maxWaitMsOpt));

    }

    public static String join(char delimiter, String... pieces) {
        StringBuilder builder = new StringBuilder();

        for (Iterator<String> iter = asList(pieces).iterator(); iter.hasNext(); ) {
            builder.append(iter.next());
            if (iter.hasNext())
                builder.append(delimiter);
        }

        return builder.toString();
    }

    /**
     * Print usage and exit
     */
    public static void printUsageAndDie(OptionParser parser , String message) throws IOException {
        System.err.println(message);
        parser.printHelpOn(System.err);
        System.exit(1);
    }

    @Test
    public void supportsShortOptions() {
        OptionParser parser = new OptionParser( "aB?*." );

        OptionSet options = parser.parse( "-a", "-B", "-?" );
        System.out.println(options.valueOf("a"));
        assertTrue( options.has( "a" ) );
        assertTrue( options.has( "B" ) );
        assertTrue( options.has( "?" ) );
        assertFalse( options.has( "." ) );

    }

    @Test
    public void allowsOptionsToAcceptArguments() {
        OptionParser parser = new OptionParser( "fc:q::" );
        OptionSet options = parser.parse( "-f", "-c", "foo", "-q" );
        assertTrue( options.has( "f" ) );
        assertTrue( options.has( "c" ) );
        assertTrue( options.hasArgument( "c" ) );
        assertEquals( "foo", options.valueOf( "c" ) );
        assertEquals( singletonList( "foo" ), options.valuesOf( "c" ) );
        assertTrue( options.has( "q" ) );
        assertFalse( options.hasArgument( "q" ) );
        assertNull( options.valueOf( "q" ) );
        assertEquals( emptyList(), options.valuesOf( "q" ) );
    }

    @Test
    public void allowsDifferentFormsOfPairingArgumentWithOption() {
        OptionParser parser = new OptionParser( "a:b:c::" );
        OptionSet options = parser.parse( "-a", "foo", "-bbar", "-c=baz" );
        assertTrue( options.has( "a" ) );
        assertTrue( options.hasArgument( "a" ) );
        assertEquals( "foo", options.valueOf( "a" ) );
        assertTrue( options.has( "b" ) );
        assertTrue( options.hasArgument( "b" ) );
        assertEquals( "bar", options.valueOf( "b" ) );
        assertTrue( options.has( "c" ) );
        assertTrue( options.hasArgument( "c" ) );
        assertEquals( "baz", options.valueOf( "c" ) );
    }

    // 允许多个短选项串写
    @Test
    public void allowsClusteringShortOptions() {
        OptionParser parser = new OptionParser( "aBcd" );
        OptionSet options = parser.parse( "-cdBa" );
        assertTrue( options.has( "a" ) );
        assertTrue( options.has( "B" ) );
        assertTrue( options.has( "c" ) );
        assertTrue( options.has( "d" ) );
    }

    @Test
    public void allowsClusteringShortOptionsThatAcceptArguments() {
        OptionParser parser = new OptionParser();
        parser.accepts( "a" );
        parser.accepts( "B" );
        parser.accepts( "c" ).withRequiredArg();

        OptionSet options = parser.parse( "-aBcfoo" );

        assertTrue( options.has( "a" ) );
        assertTrue( options.has( "B" ) );
        assertTrue( options.has( "c" ) );
        assertEquals( "foo", options.valueOf( "c" ) );
    }

}
