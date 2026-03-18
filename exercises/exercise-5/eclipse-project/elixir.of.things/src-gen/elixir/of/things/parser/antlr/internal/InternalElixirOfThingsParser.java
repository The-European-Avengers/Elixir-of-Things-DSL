package elixir.of.things.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import elixir.of.things.services.ElixirOfThingsGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalElixirOfThingsParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'system'", "'{'", "'}'", "'broker:'", "'port:'", "'node'", "'topic'", "'='", "'('", "','", "')'", "'sensor'", "'type:'", "'gpioPin:'", "'sampleRate:'", "'deployedOn:'", "'trigger'", "'when'", "'value'", "':'", "'publish'", "'timestamp'", "'actuator'", "'subscribeTo:'", "'on'", "'message'", "'from'", "'turn'", "'for'", "'coordinator'", "'rule'", "'message:'", "'TEMP_DS18B20'", "'TEMP_DHT22'", "'HUMIDITY'", "'MOTION_PIR'", "'LIGHT'", "'DISTANCE'", "'RELAY'", "'LED'", "'BUZZER'", "'SERVO'", "'AT_MOST_ONCE'", "'AT_LEAST_ONCE'", "'EXACTLY_ONCE'", "'>'", "'<'", "'>='", "'<='", "'=='", "'and'", "'or'", "'ON'", "'OFF'", "'sec'", "'min'", "'ms'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__59=59;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__55=55;
    public static final int T__12=12;
    public static final int T__56=56;
    public static final int T__13=13;
    public static final int T__57=57;
    public static final int T__14=14;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalElixirOfThingsParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalElixirOfThingsParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalElixirOfThingsParser.tokenNames; }
    public String getGrammarFileName() { return "InternalElixirOfThings.g"; }



     	private ElixirOfThingsGrammarAccess grammarAccess;

        public InternalElixirOfThingsParser(TokenStream input, ElixirOfThingsGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "System";
       	}

       	@Override
       	protected ElixirOfThingsGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSystem"
    // InternalElixirOfThings.g:65:1: entryRuleSystem returns [EObject current=null] : iv_ruleSystem= ruleSystem EOF ;
    public final EObject entryRuleSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSystem = null;


        try {
            // InternalElixirOfThings.g:65:47: (iv_ruleSystem= ruleSystem EOF )
            // InternalElixirOfThings.g:66:2: iv_ruleSystem= ruleSystem EOF
            {
             newCompositeNode(grammarAccess.getSystemRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSystem=ruleSystem();

            state._fsp--;

             current =iv_ruleSystem; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSystem"


    // $ANTLR start "ruleSystem"
    // InternalElixirOfThings.g:72:1: ruleSystem returns [EObject current=null] : (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_broker_3_0= ruleBroker ) ) ( (lv_nodes_4_0= ruleNode ) )+ ( (lv_topics_5_0= ruleTopic ) )* ( ( (lv_sensors_6_0= ruleSensor ) ) | ( (lv_actuators_7_0= ruleActuator ) ) | ( (lv_coordinators_8_0= ruleCoordinator ) ) )* otherlv_9= '}' ) ;
    public final EObject ruleSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_9=null;
        EObject lv_broker_3_0 = null;

        EObject lv_nodes_4_0 = null;

        EObject lv_topics_5_0 = null;

        EObject lv_sensors_6_0 = null;

        EObject lv_actuators_7_0 = null;

        EObject lv_coordinators_8_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:78:2: ( (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_broker_3_0= ruleBroker ) ) ( (lv_nodes_4_0= ruleNode ) )+ ( (lv_topics_5_0= ruleTopic ) )* ( ( (lv_sensors_6_0= ruleSensor ) ) | ( (lv_actuators_7_0= ruleActuator ) ) | ( (lv_coordinators_8_0= ruleCoordinator ) ) )* otherlv_9= '}' ) )
            // InternalElixirOfThings.g:79:2: (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_broker_3_0= ruleBroker ) ) ( (lv_nodes_4_0= ruleNode ) )+ ( (lv_topics_5_0= ruleTopic ) )* ( ( (lv_sensors_6_0= ruleSensor ) ) | ( (lv_actuators_7_0= ruleActuator ) ) | ( (lv_coordinators_8_0= ruleCoordinator ) ) )* otherlv_9= '}' )
            {
            // InternalElixirOfThings.g:79:2: (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_broker_3_0= ruleBroker ) ) ( (lv_nodes_4_0= ruleNode ) )+ ( (lv_topics_5_0= ruleTopic ) )* ( ( (lv_sensors_6_0= ruleSensor ) ) | ( (lv_actuators_7_0= ruleActuator ) ) | ( (lv_coordinators_8_0= ruleCoordinator ) ) )* otherlv_9= '}' )
            // InternalElixirOfThings.g:80:3: otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_broker_3_0= ruleBroker ) ) ( (lv_nodes_4_0= ruleNode ) )+ ( (lv_topics_5_0= ruleTopic ) )* ( ( (lv_sensors_6_0= ruleSensor ) ) | ( (lv_actuators_7_0= ruleActuator ) ) | ( (lv_coordinators_8_0= ruleCoordinator ) ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getSystemAccess().getSystemKeyword_0());
            		
            // InternalElixirOfThings.g:84:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:85:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:85:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:86:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getSystemAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSystemRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalElixirOfThings.g:106:3: ( (lv_broker_3_0= ruleBroker ) )
            // InternalElixirOfThings.g:107:4: (lv_broker_3_0= ruleBroker )
            {
            // InternalElixirOfThings.g:107:4: (lv_broker_3_0= ruleBroker )
            // InternalElixirOfThings.g:108:5: lv_broker_3_0= ruleBroker
            {

            					newCompositeNode(grammarAccess.getSystemAccess().getBrokerBrokerParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_6);
            lv_broker_3_0=ruleBroker();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSystemRule());
            					}
            					set(
            						current,
            						"broker",
            						lv_broker_3_0,
            						"elixir.of.things.ElixirOfThings.Broker");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalElixirOfThings.g:125:3: ( (lv_nodes_4_0= ruleNode ) )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==16) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalElixirOfThings.g:126:4: (lv_nodes_4_0= ruleNode )
            	    {
            	    // InternalElixirOfThings.g:126:4: (lv_nodes_4_0= ruleNode )
            	    // InternalElixirOfThings.g:127:5: lv_nodes_4_0= ruleNode
            	    {

            	    					newCompositeNode(grammarAccess.getSystemAccess().getNodesNodeParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_nodes_4_0=ruleNode();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSystemRule());
            	    					}
            	    					add(
            	    						current,
            	    						"nodes",
            	    						lv_nodes_4_0,
            	    						"elixir.of.things.ElixirOfThings.Node");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            // InternalElixirOfThings.g:144:3: ( (lv_topics_5_0= ruleTopic ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==17) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalElixirOfThings.g:145:4: (lv_topics_5_0= ruleTopic )
            	    {
            	    // InternalElixirOfThings.g:145:4: (lv_topics_5_0= ruleTopic )
            	    // InternalElixirOfThings.g:146:5: lv_topics_5_0= ruleTopic
            	    {

            	    					newCompositeNode(grammarAccess.getSystemAccess().getTopicsTopicParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_8);
            	    lv_topics_5_0=ruleTopic();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSystemRule());
            	    					}
            	    					add(
            	    						current,
            	    						"topics",
            	    						lv_topics_5_0,
            	    						"elixir.of.things.ElixirOfThings.Topic");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalElixirOfThings.g:163:3: ( ( (lv_sensors_6_0= ruleSensor ) ) | ( (lv_actuators_7_0= ruleActuator ) ) | ( (lv_coordinators_8_0= ruleCoordinator ) ) )*
            loop3:
            do {
                int alt3=4;
                switch ( input.LA(1) ) {
                case 22:
                    {
                    alt3=1;
                    }
                    break;
                case 33:
                    {
                    alt3=2;
                    }
                    break;
                case 40:
                    {
                    alt3=3;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // InternalElixirOfThings.g:164:4: ( (lv_sensors_6_0= ruleSensor ) )
            	    {
            	    // InternalElixirOfThings.g:164:4: ( (lv_sensors_6_0= ruleSensor ) )
            	    // InternalElixirOfThings.g:165:5: (lv_sensors_6_0= ruleSensor )
            	    {
            	    // InternalElixirOfThings.g:165:5: (lv_sensors_6_0= ruleSensor )
            	    // InternalElixirOfThings.g:166:6: lv_sensors_6_0= ruleSensor
            	    {

            	    						newCompositeNode(grammarAccess.getSystemAccess().getSensorsSensorParserRuleCall_6_0_0());
            	    					
            	    pushFollow(FOLLOW_9);
            	    lv_sensors_6_0=ruleSensor();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSystemRule());
            	    						}
            	    						add(
            	    							current,
            	    							"sensors",
            	    							lv_sensors_6_0,
            	    							"elixir.of.things.ElixirOfThings.Sensor");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalElixirOfThings.g:184:4: ( (lv_actuators_7_0= ruleActuator ) )
            	    {
            	    // InternalElixirOfThings.g:184:4: ( (lv_actuators_7_0= ruleActuator ) )
            	    // InternalElixirOfThings.g:185:5: (lv_actuators_7_0= ruleActuator )
            	    {
            	    // InternalElixirOfThings.g:185:5: (lv_actuators_7_0= ruleActuator )
            	    // InternalElixirOfThings.g:186:6: lv_actuators_7_0= ruleActuator
            	    {

            	    						newCompositeNode(grammarAccess.getSystemAccess().getActuatorsActuatorParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_9);
            	    lv_actuators_7_0=ruleActuator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSystemRule());
            	    						}
            	    						add(
            	    							current,
            	    							"actuators",
            	    							lv_actuators_7_0,
            	    							"elixir.of.things.ElixirOfThings.Actuator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalElixirOfThings.g:204:4: ( (lv_coordinators_8_0= ruleCoordinator ) )
            	    {
            	    // InternalElixirOfThings.g:204:4: ( (lv_coordinators_8_0= ruleCoordinator ) )
            	    // InternalElixirOfThings.g:205:5: (lv_coordinators_8_0= ruleCoordinator )
            	    {
            	    // InternalElixirOfThings.g:205:5: (lv_coordinators_8_0= ruleCoordinator )
            	    // InternalElixirOfThings.g:206:6: lv_coordinators_8_0= ruleCoordinator
            	    {

            	    						newCompositeNode(grammarAccess.getSystemAccess().getCoordinatorsCoordinatorParserRuleCall_6_2_0());
            	    					
            	    pushFollow(FOLLOW_9);
            	    lv_coordinators_8_0=ruleCoordinator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSystemRule());
            	    						}
            	    						add(
            	    							current,
            	    							"coordinators",
            	    							lv_coordinators_8_0,
            	    							"elixir.of.things.ElixirOfThings.Coordinator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            otherlv_9=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSystem"


    // $ANTLR start "entryRuleBroker"
    // InternalElixirOfThings.g:232:1: entryRuleBroker returns [EObject current=null] : iv_ruleBroker= ruleBroker EOF ;
    public final EObject entryRuleBroker() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBroker = null;


        try {
            // InternalElixirOfThings.g:232:47: (iv_ruleBroker= ruleBroker EOF )
            // InternalElixirOfThings.g:233:2: iv_ruleBroker= ruleBroker EOF
            {
             newCompositeNode(grammarAccess.getBrokerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBroker=ruleBroker();

            state._fsp--;

             current =iv_ruleBroker; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBroker"


    // $ANTLR start "ruleBroker"
    // InternalElixirOfThings.g:239:1: ruleBroker returns [EObject current=null] : (otherlv_0= 'broker:' ( (lv_host_1_0= RULE_STRING ) ) otherlv_2= 'port:' ( (lv_port_3_0= RULE_INT ) ) ) ;
    public final EObject ruleBroker() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_host_1_0=null;
        Token otherlv_2=null;
        Token lv_port_3_0=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:245:2: ( (otherlv_0= 'broker:' ( (lv_host_1_0= RULE_STRING ) ) otherlv_2= 'port:' ( (lv_port_3_0= RULE_INT ) ) ) )
            // InternalElixirOfThings.g:246:2: (otherlv_0= 'broker:' ( (lv_host_1_0= RULE_STRING ) ) otherlv_2= 'port:' ( (lv_port_3_0= RULE_INT ) ) )
            {
            // InternalElixirOfThings.g:246:2: (otherlv_0= 'broker:' ( (lv_host_1_0= RULE_STRING ) ) otherlv_2= 'port:' ( (lv_port_3_0= RULE_INT ) ) )
            // InternalElixirOfThings.g:247:3: otherlv_0= 'broker:' ( (lv_host_1_0= RULE_STRING ) ) otherlv_2= 'port:' ( (lv_port_3_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_10); 

            			newLeafNode(otherlv_0, grammarAccess.getBrokerAccess().getBrokerKeyword_0());
            		
            // InternalElixirOfThings.g:251:3: ( (lv_host_1_0= RULE_STRING ) )
            // InternalElixirOfThings.g:252:4: (lv_host_1_0= RULE_STRING )
            {
            // InternalElixirOfThings.g:252:4: (lv_host_1_0= RULE_STRING )
            // InternalElixirOfThings.g:253:5: lv_host_1_0= RULE_STRING
            {
            lv_host_1_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            					newLeafNode(lv_host_1_0, grammarAccess.getBrokerAccess().getHostSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBrokerRule());
            					}
            					setWithLastConsumed(
            						current,
            						"host",
            						lv_host_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,15,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getBrokerAccess().getPortKeyword_2());
            		
            // InternalElixirOfThings.g:273:3: ( (lv_port_3_0= RULE_INT ) )
            // InternalElixirOfThings.g:274:4: (lv_port_3_0= RULE_INT )
            {
            // InternalElixirOfThings.g:274:4: (lv_port_3_0= RULE_INT )
            // InternalElixirOfThings.g:275:5: lv_port_3_0= RULE_INT
            {
            lv_port_3_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_port_3_0, grammarAccess.getBrokerAccess().getPortINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBrokerRule());
            					}
            					setWithLastConsumed(
            						current,
            						"port",
            						lv_port_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBroker"


    // $ANTLR start "entryRuleNode"
    // InternalElixirOfThings.g:295:1: entryRuleNode returns [EObject current=null] : iv_ruleNode= ruleNode EOF ;
    public final EObject entryRuleNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNode = null;


        try {
            // InternalElixirOfThings.g:295:45: (iv_ruleNode= ruleNode EOF )
            // InternalElixirOfThings.g:296:2: iv_ruleNode= ruleNode EOF
            {
             newCompositeNode(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNode=ruleNode();

            state._fsp--;

             current =iv_ruleNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalElixirOfThings.g:302:1: ruleNode returns [EObject current=null] : (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject ruleNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:308:2: ( (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) ) )
            // InternalElixirOfThings.g:309:2: (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) )
            {
            // InternalElixirOfThings.g:309:2: (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) )
            // InternalElixirOfThings.g:310:3: otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,16,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getNodeAccess().getNodeKeyword_0());
            		
            // InternalElixirOfThings.g:314:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:315:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:315:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:316:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(lv_name_1_0, grammarAccess.getNodeAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRuleTopic"
    // InternalElixirOfThings.g:336:1: entryRuleTopic returns [EObject current=null] : iv_ruleTopic= ruleTopic EOF ;
    public final EObject entryRuleTopic() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopic = null;


        try {
            // InternalElixirOfThings.g:336:46: (iv_ruleTopic= ruleTopic EOF )
            // InternalElixirOfThings.g:337:2: iv_ruleTopic= ruleTopic EOF
            {
             newCompositeNode(grammarAccess.getTopicRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTopic=ruleTopic();

            state._fsp--;

             current =iv_ruleTopic; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTopic"


    // $ANTLR start "ruleTopic"
    // InternalElixirOfThings.g:343:1: ruleTopic returns [EObject current=null] : (otherlv_0= 'topic' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' otherlv_3= '(' ( (lv_topicString_4_0= RULE_STRING ) ) otherlv_5= ',' ( (lv_qos_6_0= ruleQoS ) ) otherlv_7= ')' ) ;
    public final EObject ruleTopic() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_topicString_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Enumerator lv_qos_6_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:349:2: ( (otherlv_0= 'topic' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' otherlv_3= '(' ( (lv_topicString_4_0= RULE_STRING ) ) otherlv_5= ',' ( (lv_qos_6_0= ruleQoS ) ) otherlv_7= ')' ) )
            // InternalElixirOfThings.g:350:2: (otherlv_0= 'topic' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' otherlv_3= '(' ( (lv_topicString_4_0= RULE_STRING ) ) otherlv_5= ',' ( (lv_qos_6_0= ruleQoS ) ) otherlv_7= ')' )
            {
            // InternalElixirOfThings.g:350:2: (otherlv_0= 'topic' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' otherlv_3= '(' ( (lv_topicString_4_0= RULE_STRING ) ) otherlv_5= ',' ( (lv_qos_6_0= ruleQoS ) ) otherlv_7= ')' )
            // InternalElixirOfThings.g:351:3: otherlv_0= 'topic' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' otherlv_3= '(' ( (lv_topicString_4_0= RULE_STRING ) ) otherlv_5= ',' ( (lv_qos_6_0= ruleQoS ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getTopicAccess().getTopicKeyword_0());
            		
            // InternalElixirOfThings.g:355:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:356:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:356:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:357:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(lv_name_1_0, grammarAccess.getTopicAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTopicRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,18,FOLLOW_14); 

            			newLeafNode(otherlv_2, grammarAccess.getTopicAccess().getEqualsSignKeyword_2());
            		
            otherlv_3=(Token)match(input,19,FOLLOW_10); 

            			newLeafNode(otherlv_3, grammarAccess.getTopicAccess().getLeftParenthesisKeyword_3());
            		
            // InternalElixirOfThings.g:381:3: ( (lv_topicString_4_0= RULE_STRING ) )
            // InternalElixirOfThings.g:382:4: (lv_topicString_4_0= RULE_STRING )
            {
            // InternalElixirOfThings.g:382:4: (lv_topicString_4_0= RULE_STRING )
            // InternalElixirOfThings.g:383:5: lv_topicString_4_0= RULE_STRING
            {
            lv_topicString_4_0=(Token)match(input,RULE_STRING,FOLLOW_15); 

            					newLeafNode(lv_topicString_4_0, grammarAccess.getTopicAccess().getTopicStringSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTopicRule());
            					}
            					setWithLastConsumed(
            						current,
            						"topicString",
            						lv_topicString_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,20,FOLLOW_16); 

            			newLeafNode(otherlv_5, grammarAccess.getTopicAccess().getCommaKeyword_5());
            		
            // InternalElixirOfThings.g:403:3: ( (lv_qos_6_0= ruleQoS ) )
            // InternalElixirOfThings.g:404:4: (lv_qos_6_0= ruleQoS )
            {
            // InternalElixirOfThings.g:404:4: (lv_qos_6_0= ruleQoS )
            // InternalElixirOfThings.g:405:5: lv_qos_6_0= ruleQoS
            {

            					newCompositeNode(grammarAccess.getTopicAccess().getQosQoSEnumRuleCall_6_0());
            				
            pushFollow(FOLLOW_17);
            lv_qos_6_0=ruleQoS();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTopicRule());
            					}
            					set(
            						current,
            						"qos",
            						lv_qos_6_0,
            						"elixir.of.things.ElixirOfThings.QoS");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,21,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getTopicAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTopic"


    // $ANTLR start "entryRuleSensor"
    // InternalElixirOfThings.g:430:1: entryRuleSensor returns [EObject current=null] : iv_ruleSensor= ruleSensor EOF ;
    public final EObject entryRuleSensor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSensor = null;


        try {
            // InternalElixirOfThings.g:430:47: (iv_ruleSensor= ruleSensor EOF )
            // InternalElixirOfThings.g:431:2: iv_ruleSensor= ruleSensor EOF
            {
             newCompositeNode(grammarAccess.getSensorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSensor=ruleSensor();

            state._fsp--;

             current =iv_ruleSensor; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSensor"


    // $ANTLR start "ruleSensor"
    // InternalElixirOfThings.g:437:1: ruleSensor returns [EObject current=null] : (otherlv_0= 'sensor' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleSensorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'sampleRate:' ( (lv_sampleRate_8_0= ruleDuration ) ) otherlv_9= 'deployedOn:' ( (otherlv_10= RULE_ID ) ) ( (lv_triggers_11_0= ruleTrigger ) )* otherlv_12= '}' ) ;
    public final EObject ruleSensor() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_gpioPin_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Enumerator lv_type_4_0 = null;

        EObject lv_sampleRate_8_0 = null;

        EObject lv_triggers_11_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:443:2: ( (otherlv_0= 'sensor' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleSensorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'sampleRate:' ( (lv_sampleRate_8_0= ruleDuration ) ) otherlv_9= 'deployedOn:' ( (otherlv_10= RULE_ID ) ) ( (lv_triggers_11_0= ruleTrigger ) )* otherlv_12= '}' ) )
            // InternalElixirOfThings.g:444:2: (otherlv_0= 'sensor' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleSensorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'sampleRate:' ( (lv_sampleRate_8_0= ruleDuration ) ) otherlv_9= 'deployedOn:' ( (otherlv_10= RULE_ID ) ) ( (lv_triggers_11_0= ruleTrigger ) )* otherlv_12= '}' )
            {
            // InternalElixirOfThings.g:444:2: (otherlv_0= 'sensor' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleSensorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'sampleRate:' ( (lv_sampleRate_8_0= ruleDuration ) ) otherlv_9= 'deployedOn:' ( (otherlv_10= RULE_ID ) ) ( (lv_triggers_11_0= ruleTrigger ) )* otherlv_12= '}' )
            // InternalElixirOfThings.g:445:3: otherlv_0= 'sensor' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleSensorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'sampleRate:' ( (lv_sampleRate_8_0= ruleDuration ) ) otherlv_9= 'deployedOn:' ( (otherlv_10= RULE_ID ) ) ( (lv_triggers_11_0= ruleTrigger ) )* otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,22,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getSensorAccess().getSensorKeyword_0());
            		
            // InternalElixirOfThings.g:449:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:450:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:450:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:451:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getSensorAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSensorRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getSensorAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,23,FOLLOW_19); 

            			newLeafNode(otherlv_3, grammarAccess.getSensorAccess().getTypeKeyword_3());
            		
            // InternalElixirOfThings.g:475:3: ( (lv_type_4_0= ruleSensorType ) )
            // InternalElixirOfThings.g:476:4: (lv_type_4_0= ruleSensorType )
            {
            // InternalElixirOfThings.g:476:4: (lv_type_4_0= ruleSensorType )
            // InternalElixirOfThings.g:477:5: lv_type_4_0= ruleSensorType
            {

            					newCompositeNode(grammarAccess.getSensorAccess().getTypeSensorTypeEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_20);
            lv_type_4_0=ruleSensorType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSensorRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_4_0,
            						"elixir.of.things.ElixirOfThings.SensorType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_12); 

            			newLeafNode(otherlv_5, grammarAccess.getSensorAccess().getGpioPinKeyword_5());
            		
            // InternalElixirOfThings.g:498:3: ( (lv_gpioPin_6_0= RULE_INT ) )
            // InternalElixirOfThings.g:499:4: (lv_gpioPin_6_0= RULE_INT )
            {
            // InternalElixirOfThings.g:499:4: (lv_gpioPin_6_0= RULE_INT )
            // InternalElixirOfThings.g:500:5: lv_gpioPin_6_0= RULE_INT
            {
            lv_gpioPin_6_0=(Token)match(input,RULE_INT,FOLLOW_21); 

            					newLeafNode(lv_gpioPin_6_0, grammarAccess.getSensorAccess().getGpioPinINTTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSensorRule());
            					}
            					setWithLastConsumed(
            						current,
            						"gpioPin",
            						lv_gpioPin_6_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_7=(Token)match(input,25,FOLLOW_12); 

            			newLeafNode(otherlv_7, grammarAccess.getSensorAccess().getSampleRateKeyword_7());
            		
            // InternalElixirOfThings.g:520:3: ( (lv_sampleRate_8_0= ruleDuration ) )
            // InternalElixirOfThings.g:521:4: (lv_sampleRate_8_0= ruleDuration )
            {
            // InternalElixirOfThings.g:521:4: (lv_sampleRate_8_0= ruleDuration )
            // InternalElixirOfThings.g:522:5: lv_sampleRate_8_0= ruleDuration
            {

            					newCompositeNode(grammarAccess.getSensorAccess().getSampleRateDurationParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_22);
            lv_sampleRate_8_0=ruleDuration();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSensorRule());
            					}
            					set(
            						current,
            						"sampleRate",
            						lv_sampleRate_8_0,
            						"elixir.of.things.ElixirOfThings.Duration");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_9=(Token)match(input,26,FOLLOW_3); 

            			newLeafNode(otherlv_9, grammarAccess.getSensorAccess().getDeployedOnKeyword_9());
            		
            // InternalElixirOfThings.g:543:3: ( (otherlv_10= RULE_ID ) )
            // InternalElixirOfThings.g:544:4: (otherlv_10= RULE_ID )
            {
            // InternalElixirOfThings.g:544:4: (otherlv_10= RULE_ID )
            // InternalElixirOfThings.g:545:5: otherlv_10= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSensorRule());
            					}
            				
            otherlv_10=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(otherlv_10, grammarAccess.getSensorAccess().getDeployedOnNodeCrossReference_10_0());
            				

            }


            }

            // InternalElixirOfThings.g:556:3: ( (lv_triggers_11_0= ruleTrigger ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==27) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalElixirOfThings.g:557:4: (lv_triggers_11_0= ruleTrigger )
            	    {
            	    // InternalElixirOfThings.g:557:4: (lv_triggers_11_0= ruleTrigger )
            	    // InternalElixirOfThings.g:558:5: lv_triggers_11_0= ruleTrigger
            	    {

            	    					newCompositeNode(grammarAccess.getSensorAccess().getTriggersTriggerParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_23);
            	    lv_triggers_11_0=ruleTrigger();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSensorRule());
            	    					}
            	    					add(
            	    						current,
            	    						"triggers",
            	    						lv_triggers_11_0,
            	    						"elixir.of.things.ElixirOfThings.Trigger");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            otherlv_12=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_12, grammarAccess.getSensorAccess().getRightCurlyBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSensor"


    // $ANTLR start "entryRuleTrigger"
    // InternalElixirOfThings.g:583:1: entryRuleTrigger returns [EObject current=null] : iv_ruleTrigger= ruleTrigger EOF ;
    public final EObject entryRuleTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrigger = null;


        try {
            // InternalElixirOfThings.g:583:48: (iv_ruleTrigger= ruleTrigger EOF )
            // InternalElixirOfThings.g:584:2: iv_ruleTrigger= ruleTrigger EOF
            {
             newCompositeNode(grammarAccess.getTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTrigger=ruleTrigger();

            state._fsp--;

             current =iv_ruleTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTrigger"


    // $ANTLR start "ruleTrigger"
    // InternalElixirOfThings.g:590:1: ruleTrigger returns [EObject current=null] : (otherlv_0= 'trigger' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleTriggerCondition ) ) ( (lv_actions_4_0= ruleTriggerAction ) )+ otherlv_5= '}' ) ;
    public final EObject ruleTrigger() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_condition_3_0 = null;

        EObject lv_actions_4_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:596:2: ( (otherlv_0= 'trigger' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleTriggerCondition ) ) ( (lv_actions_4_0= ruleTriggerAction ) )+ otherlv_5= '}' ) )
            // InternalElixirOfThings.g:597:2: (otherlv_0= 'trigger' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleTriggerCondition ) ) ( (lv_actions_4_0= ruleTriggerAction ) )+ otherlv_5= '}' )
            {
            // InternalElixirOfThings.g:597:2: (otherlv_0= 'trigger' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleTriggerCondition ) ) ( (lv_actions_4_0= ruleTriggerAction ) )+ otherlv_5= '}' )
            // InternalElixirOfThings.g:598:3: otherlv_0= 'trigger' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleTriggerCondition ) ) ( (lv_actions_4_0= ruleTriggerAction ) )+ otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,27,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getTriggerAccess().getTriggerKeyword_0());
            		
            // InternalElixirOfThings.g:602:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:603:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:603:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:604:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getTriggerAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTriggerRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_24); 

            			newLeafNode(otherlv_2, grammarAccess.getTriggerAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalElixirOfThings.g:624:3: ( (lv_condition_3_0= ruleTriggerCondition ) )
            // InternalElixirOfThings.g:625:4: (lv_condition_3_0= ruleTriggerCondition )
            {
            // InternalElixirOfThings.g:625:4: (lv_condition_3_0= ruleTriggerCondition )
            // InternalElixirOfThings.g:626:5: lv_condition_3_0= ruleTriggerCondition
            {

            					newCompositeNode(grammarAccess.getTriggerAccess().getConditionTriggerConditionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_25);
            lv_condition_3_0=ruleTriggerCondition();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTriggerRule());
            					}
            					set(
            						current,
            						"condition",
            						lv_condition_3_0,
            						"elixir.of.things.ElixirOfThings.TriggerCondition");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalElixirOfThings.g:643:3: ( (lv_actions_4_0= ruleTriggerAction ) )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==31) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalElixirOfThings.g:644:4: (lv_actions_4_0= ruleTriggerAction )
            	    {
            	    // InternalElixirOfThings.g:644:4: (lv_actions_4_0= ruleTriggerAction )
            	    // InternalElixirOfThings.g:645:5: lv_actions_4_0= ruleTriggerAction
            	    {

            	    					newCompositeNode(grammarAccess.getTriggerAccess().getActionsTriggerActionParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_26);
            	    lv_actions_4_0=ruleTriggerAction();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getTriggerRule());
            	    					}
            	    					add(
            	    						current,
            	    						"actions",
            	    						lv_actions_4_0,
            	    						"elixir.of.things.ElixirOfThings.TriggerAction");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            otherlv_5=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getTriggerAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrigger"


    // $ANTLR start "entryRuleTriggerCondition"
    // InternalElixirOfThings.g:670:1: entryRuleTriggerCondition returns [EObject current=null] : iv_ruleTriggerCondition= ruleTriggerCondition EOF ;
    public final EObject entryRuleTriggerCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTriggerCondition = null;


        try {
            // InternalElixirOfThings.g:670:57: (iv_ruleTriggerCondition= ruleTriggerCondition EOF )
            // InternalElixirOfThings.g:671:2: iv_ruleTriggerCondition= ruleTriggerCondition EOF
            {
             newCompositeNode(grammarAccess.getTriggerConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTriggerCondition=ruleTriggerCondition();

            state._fsp--;

             current =iv_ruleTriggerCondition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTriggerCondition"


    // $ANTLR start "ruleTriggerCondition"
    // InternalElixirOfThings.g:677:1: ruleTriggerCondition returns [EObject current=null] : (otherlv_0= 'when' otherlv_1= 'value' ( (lv_operator_2_0= ruleOperator ) ) ( (lv_right_3_0= RULE_INT ) ) otherlv_4= ':' ) ;
    public final EObject ruleTriggerCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_right_3_0=null;
        Token otherlv_4=null;
        Enumerator lv_operator_2_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:683:2: ( (otherlv_0= 'when' otherlv_1= 'value' ( (lv_operator_2_0= ruleOperator ) ) ( (lv_right_3_0= RULE_INT ) ) otherlv_4= ':' ) )
            // InternalElixirOfThings.g:684:2: (otherlv_0= 'when' otherlv_1= 'value' ( (lv_operator_2_0= ruleOperator ) ) ( (lv_right_3_0= RULE_INT ) ) otherlv_4= ':' )
            {
            // InternalElixirOfThings.g:684:2: (otherlv_0= 'when' otherlv_1= 'value' ( (lv_operator_2_0= ruleOperator ) ) ( (lv_right_3_0= RULE_INT ) ) otherlv_4= ':' )
            // InternalElixirOfThings.g:685:3: otherlv_0= 'when' otherlv_1= 'value' ( (lv_operator_2_0= ruleOperator ) ) ( (lv_right_3_0= RULE_INT ) ) otherlv_4= ':'
            {
            otherlv_0=(Token)match(input,28,FOLLOW_27); 

            			newLeafNode(otherlv_0, grammarAccess.getTriggerConditionAccess().getWhenKeyword_0());
            		
            otherlv_1=(Token)match(input,29,FOLLOW_28); 

            			newLeafNode(otherlv_1, grammarAccess.getTriggerConditionAccess().getValueKeyword_1());
            		
            // InternalElixirOfThings.g:693:3: ( (lv_operator_2_0= ruleOperator ) )
            // InternalElixirOfThings.g:694:4: (lv_operator_2_0= ruleOperator )
            {
            // InternalElixirOfThings.g:694:4: (lv_operator_2_0= ruleOperator )
            // InternalElixirOfThings.g:695:5: lv_operator_2_0= ruleOperator
            {

            					newCompositeNode(grammarAccess.getTriggerConditionAccess().getOperatorOperatorEnumRuleCall_2_0());
            				
            pushFollow(FOLLOW_12);
            lv_operator_2_0=ruleOperator();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTriggerConditionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_2_0,
            						"elixir.of.things.ElixirOfThings.Operator");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalElixirOfThings.g:712:3: ( (lv_right_3_0= RULE_INT ) )
            // InternalElixirOfThings.g:713:4: (lv_right_3_0= RULE_INT )
            {
            // InternalElixirOfThings.g:713:4: (lv_right_3_0= RULE_INT )
            // InternalElixirOfThings.g:714:5: lv_right_3_0= RULE_INT
            {
            lv_right_3_0=(Token)match(input,RULE_INT,FOLLOW_29); 

            					newLeafNode(lv_right_3_0, grammarAccess.getTriggerConditionAccess().getRightINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTriggerConditionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"right",
            						lv_right_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_4=(Token)match(input,30,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getTriggerConditionAccess().getColonKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTriggerCondition"


    // $ANTLR start "entryRuleTriggerAction"
    // InternalElixirOfThings.g:738:1: entryRuleTriggerAction returns [EObject current=null] : iv_ruleTriggerAction= ruleTriggerAction EOF ;
    public final EObject entryRuleTriggerAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTriggerAction = null;


        try {
            // InternalElixirOfThings.g:738:54: (iv_ruleTriggerAction= ruleTriggerAction EOF )
            // InternalElixirOfThings.g:739:2: iv_ruleTriggerAction= ruleTriggerAction EOF
            {
             newCompositeNode(grammarAccess.getTriggerActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTriggerAction=ruleTriggerAction();

            state._fsp--;

             current =iv_ruleTriggerAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTriggerAction"


    // $ANTLR start "ruleTriggerAction"
    // InternalElixirOfThings.g:745:1: ruleTriggerAction returns [EObject current=null] : (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' ( (lv_fields_4_0= rulePublishField ) ) (otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) ) )* otherlv_7= '}' ) ;
    public final EObject ruleTriggerAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_fields_4_0 = null;

        EObject lv_fields_6_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:751:2: ( (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' ( (lv_fields_4_0= rulePublishField ) ) (otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) ) )* otherlv_7= '}' ) )
            // InternalElixirOfThings.g:752:2: (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' ( (lv_fields_4_0= rulePublishField ) ) (otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) ) )* otherlv_7= '}' )
            {
            // InternalElixirOfThings.g:752:2: (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' ( (lv_fields_4_0= rulePublishField ) ) (otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) ) )* otherlv_7= '}' )
            // InternalElixirOfThings.g:753:3: otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' ( (lv_fields_4_0= rulePublishField ) ) (otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,31,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getTriggerActionAccess().getPublishKeyword_0());
            		
            // InternalElixirOfThings.g:757:3: ( (otherlv_1= RULE_ID ) )
            // InternalElixirOfThings.g:758:4: (otherlv_1= RULE_ID )
            {
            // InternalElixirOfThings.g:758:4: (otherlv_1= RULE_ID )
            // InternalElixirOfThings.g:759:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTriggerActionRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(otherlv_1, grammarAccess.getTriggerActionAccess().getTopicTopicCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,30,FOLLOW_4); 

            			newLeafNode(otherlv_2, grammarAccess.getTriggerActionAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,12,FOLLOW_30); 

            			newLeafNode(otherlv_3, grammarAccess.getTriggerActionAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalElixirOfThings.g:778:3: ( (lv_fields_4_0= rulePublishField ) )
            // InternalElixirOfThings.g:779:4: (lv_fields_4_0= rulePublishField )
            {
            // InternalElixirOfThings.g:779:4: (lv_fields_4_0= rulePublishField )
            // InternalElixirOfThings.g:780:5: lv_fields_4_0= rulePublishField
            {

            					newCompositeNode(grammarAccess.getTriggerActionAccess().getFieldsPublishFieldParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_31);
            lv_fields_4_0=rulePublishField();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTriggerActionRule());
            					}
            					add(
            						current,
            						"fields",
            						lv_fields_4_0,
            						"elixir.of.things.ElixirOfThings.PublishField");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalElixirOfThings.g:797:3: (otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==20) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalElixirOfThings.g:798:4: otherlv_5= ',' ( (lv_fields_6_0= rulePublishField ) )
            	    {
            	    otherlv_5=(Token)match(input,20,FOLLOW_30); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTriggerActionAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalElixirOfThings.g:802:4: ( (lv_fields_6_0= rulePublishField ) )
            	    // InternalElixirOfThings.g:803:5: (lv_fields_6_0= rulePublishField )
            	    {
            	    // InternalElixirOfThings.g:803:5: (lv_fields_6_0= rulePublishField )
            	    // InternalElixirOfThings.g:804:6: lv_fields_6_0= rulePublishField
            	    {

            	    						newCompositeNode(grammarAccess.getTriggerActionAccess().getFieldsPublishFieldParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_31);
            	    lv_fields_6_0=rulePublishField();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getTriggerActionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"fields",
            	    							lv_fields_6_0,
            	    							"elixir.of.things.ElixirOfThings.PublishField");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            otherlv_7=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getTriggerActionAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTriggerAction"


    // $ANTLR start "entryRulePublishField"
    // InternalElixirOfThings.g:830:1: entryRulePublishField returns [EObject current=null] : iv_rulePublishField= rulePublishField EOF ;
    public final EObject entryRulePublishField() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePublishField = null;


        try {
            // InternalElixirOfThings.g:830:53: (iv_rulePublishField= rulePublishField EOF )
            // InternalElixirOfThings.g:831:2: iv_rulePublishField= rulePublishField EOF
            {
             newCompositeNode(grammarAccess.getPublishFieldRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePublishField=rulePublishField();

            state._fsp--;

             current =iv_rulePublishField; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePublishField"


    // $ANTLR start "rulePublishField"
    // InternalElixirOfThings.g:837:1: rulePublishField returns [EObject current=null] : ( ( () otherlv_1= 'value' ) | ( () otherlv_3= 'timestamp' ) ) ;
    public final EObject rulePublishField() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:843:2: ( ( ( () otherlv_1= 'value' ) | ( () otherlv_3= 'timestamp' ) ) )
            // InternalElixirOfThings.g:844:2: ( ( () otherlv_1= 'value' ) | ( () otherlv_3= 'timestamp' ) )
            {
            // InternalElixirOfThings.g:844:2: ( ( () otherlv_1= 'value' ) | ( () otherlv_3= 'timestamp' ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==29) ) {
                alt7=1;
            }
            else if ( (LA7_0==32) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalElixirOfThings.g:845:3: ( () otherlv_1= 'value' )
                    {
                    // InternalElixirOfThings.g:845:3: ( () otherlv_1= 'value' )
                    // InternalElixirOfThings.g:846:4: () otherlv_1= 'value'
                    {
                    // InternalElixirOfThings.g:846:4: ()
                    // InternalElixirOfThings.g:847:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPublishFieldAccess().getValueFieldAction_0_0(),
                    						current);
                    				

                    }

                    otherlv_1=(Token)match(input,29,FOLLOW_2); 

                    				newLeafNode(otherlv_1, grammarAccess.getPublishFieldAccess().getValueKeyword_0_1());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:859:3: ( () otherlv_3= 'timestamp' )
                    {
                    // InternalElixirOfThings.g:859:3: ( () otherlv_3= 'timestamp' )
                    // InternalElixirOfThings.g:860:4: () otherlv_3= 'timestamp'
                    {
                    // InternalElixirOfThings.g:860:4: ()
                    // InternalElixirOfThings.g:861:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPublishFieldAccess().getTimestampFieldAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_3=(Token)match(input,32,FOLLOW_2); 

                    				newLeafNode(otherlv_3, grammarAccess.getPublishFieldAccess().getTimestampKeyword_1_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePublishField"


    // $ANTLR start "entryRuleActuator"
    // InternalElixirOfThings.g:876:1: entryRuleActuator returns [EObject current=null] : iv_ruleActuator= ruleActuator EOF ;
    public final EObject entryRuleActuator() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActuator = null;


        try {
            // InternalElixirOfThings.g:876:49: (iv_ruleActuator= ruleActuator EOF )
            // InternalElixirOfThings.g:877:2: iv_ruleActuator= ruleActuator EOF
            {
             newCompositeNode(grammarAccess.getActuatorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleActuator=ruleActuator();

            state._fsp--;

             current =iv_ruleActuator; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActuator"


    // $ANTLR start "ruleActuator"
    // InternalElixirOfThings.g:883:1: ruleActuator returns [EObject current=null] : (otherlv_0= 'actuator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleActuatorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'deployedOn:' ( (otherlv_8= RULE_ID ) ) otherlv_9= 'subscribeTo:' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ( (lv_messages_13_0= ruleOnMessage ) )* otherlv_14= '}' ) ;
    public final EObject ruleActuator() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_gpioPin_6_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Enumerator lv_type_4_0 = null;

        EObject lv_messages_13_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:889:2: ( (otherlv_0= 'actuator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleActuatorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'deployedOn:' ( (otherlv_8= RULE_ID ) ) otherlv_9= 'subscribeTo:' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ( (lv_messages_13_0= ruleOnMessage ) )* otherlv_14= '}' ) )
            // InternalElixirOfThings.g:890:2: (otherlv_0= 'actuator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleActuatorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'deployedOn:' ( (otherlv_8= RULE_ID ) ) otherlv_9= 'subscribeTo:' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ( (lv_messages_13_0= ruleOnMessage ) )* otherlv_14= '}' )
            {
            // InternalElixirOfThings.g:890:2: (otherlv_0= 'actuator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleActuatorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'deployedOn:' ( (otherlv_8= RULE_ID ) ) otherlv_9= 'subscribeTo:' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ( (lv_messages_13_0= ruleOnMessage ) )* otherlv_14= '}' )
            // InternalElixirOfThings.g:891:3: otherlv_0= 'actuator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleActuatorType ) ) otherlv_5= 'gpioPin:' ( (lv_gpioPin_6_0= RULE_INT ) ) otherlv_7= 'deployedOn:' ( (otherlv_8= RULE_ID ) ) otherlv_9= 'subscribeTo:' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ( (lv_messages_13_0= ruleOnMessage ) )* otherlv_14= '}'
            {
            otherlv_0=(Token)match(input,33,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getActuatorAccess().getActuatorKeyword_0());
            		
            // InternalElixirOfThings.g:895:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:896:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:896:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:897:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getActuatorAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getActuatorRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getActuatorAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,23,FOLLOW_32); 

            			newLeafNode(otherlv_3, grammarAccess.getActuatorAccess().getTypeKeyword_3());
            		
            // InternalElixirOfThings.g:921:3: ( (lv_type_4_0= ruleActuatorType ) )
            // InternalElixirOfThings.g:922:4: (lv_type_4_0= ruleActuatorType )
            {
            // InternalElixirOfThings.g:922:4: (lv_type_4_0= ruleActuatorType )
            // InternalElixirOfThings.g:923:5: lv_type_4_0= ruleActuatorType
            {

            					newCompositeNode(grammarAccess.getActuatorAccess().getTypeActuatorTypeEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_20);
            lv_type_4_0=ruleActuatorType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getActuatorRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_4_0,
            						"elixir.of.things.ElixirOfThings.ActuatorType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_12); 

            			newLeafNode(otherlv_5, grammarAccess.getActuatorAccess().getGpioPinKeyword_5());
            		
            // InternalElixirOfThings.g:944:3: ( (lv_gpioPin_6_0= RULE_INT ) )
            // InternalElixirOfThings.g:945:4: (lv_gpioPin_6_0= RULE_INT )
            {
            // InternalElixirOfThings.g:945:4: (lv_gpioPin_6_0= RULE_INT )
            // InternalElixirOfThings.g:946:5: lv_gpioPin_6_0= RULE_INT
            {
            lv_gpioPin_6_0=(Token)match(input,RULE_INT,FOLLOW_22); 

            					newLeafNode(lv_gpioPin_6_0, grammarAccess.getActuatorAccess().getGpioPinINTTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getActuatorRule());
            					}
            					setWithLastConsumed(
            						current,
            						"gpioPin",
            						lv_gpioPin_6_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_7=(Token)match(input,26,FOLLOW_3); 

            			newLeafNode(otherlv_7, grammarAccess.getActuatorAccess().getDeployedOnKeyword_7());
            		
            // InternalElixirOfThings.g:966:3: ( (otherlv_8= RULE_ID ) )
            // InternalElixirOfThings.g:967:4: (otherlv_8= RULE_ID )
            {
            // InternalElixirOfThings.g:967:4: (otherlv_8= RULE_ID )
            // InternalElixirOfThings.g:968:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getActuatorRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_33); 

            					newLeafNode(otherlv_8, grammarAccess.getActuatorAccess().getDeployedOnNodeCrossReference_8_0());
            				

            }


            }

            otherlv_9=(Token)match(input,34,FOLLOW_3); 

            			newLeafNode(otherlv_9, grammarAccess.getActuatorAccess().getSubscribeToKeyword_9());
            		
            // InternalElixirOfThings.g:983:3: ( (otherlv_10= RULE_ID ) )
            // InternalElixirOfThings.g:984:4: (otherlv_10= RULE_ID )
            {
            // InternalElixirOfThings.g:984:4: (otherlv_10= RULE_ID )
            // InternalElixirOfThings.g:985:5: otherlv_10= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getActuatorRule());
            					}
            				
            otherlv_10=(Token)match(input,RULE_ID,FOLLOW_34); 

            					newLeafNode(otherlv_10, grammarAccess.getActuatorAccess().getSubscribeToTopicCrossReference_10_0());
            				

            }


            }

            // InternalElixirOfThings.g:996:3: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==20) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalElixirOfThings.g:997:4: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    {
            	    otherlv_11=(Token)match(input,20,FOLLOW_3); 

            	    				newLeafNode(otherlv_11, grammarAccess.getActuatorAccess().getCommaKeyword_11_0());
            	    			
            	    // InternalElixirOfThings.g:1001:4: ( (otherlv_12= RULE_ID ) )
            	    // InternalElixirOfThings.g:1002:5: (otherlv_12= RULE_ID )
            	    {
            	    // InternalElixirOfThings.g:1002:5: (otherlv_12= RULE_ID )
            	    // InternalElixirOfThings.g:1003:6: otherlv_12= RULE_ID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getActuatorRule());
            	    						}
            	    					
            	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    						newLeafNode(otherlv_12, grammarAccess.getActuatorAccess().getSubscribeToTopicCrossReference_11_1_0());
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            // InternalElixirOfThings.g:1015:3: ( (lv_messages_13_0= ruleOnMessage ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==35) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalElixirOfThings.g:1016:4: (lv_messages_13_0= ruleOnMessage )
            	    {
            	    // InternalElixirOfThings.g:1016:4: (lv_messages_13_0= ruleOnMessage )
            	    // InternalElixirOfThings.g:1017:5: lv_messages_13_0= ruleOnMessage
            	    {

            	    					newCompositeNode(grammarAccess.getActuatorAccess().getMessagesOnMessageParserRuleCall_12_0());
            	    				
            	    pushFollow(FOLLOW_35);
            	    lv_messages_13_0=ruleOnMessage();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getActuatorRule());
            	    					}
            	    					add(
            	    						current,
            	    						"messages",
            	    						lv_messages_13_0,
            	    						"elixir.of.things.ElixirOfThings.OnMessage");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_14=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_14, grammarAccess.getActuatorAccess().getRightCurlyBracketKeyword_13());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActuator"


    // $ANTLR start "entryRuleOnMessage"
    // InternalElixirOfThings.g:1042:1: entryRuleOnMessage returns [EObject current=null] : iv_ruleOnMessage= ruleOnMessage EOF ;
    public final EObject entryRuleOnMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOnMessage = null;


        try {
            // InternalElixirOfThings.g:1042:50: (iv_ruleOnMessage= ruleOnMessage EOF )
            // InternalElixirOfThings.g:1043:2: iv_ruleOnMessage= ruleOnMessage EOF
            {
             newCompositeNode(grammarAccess.getOnMessageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOnMessage=ruleOnMessage();

            state._fsp--;

             current =iv_ruleOnMessage; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOnMessage"


    // $ANTLR start "ruleOnMessage"
    // InternalElixirOfThings.g:1049:1: ruleOnMessage returns [EObject current=null] : (otherlv_0= 'on' otherlv_1= 'message' otherlv_2= 'from' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' otherlv_5= 'turn' ( (lv_state_6_0= ruleState ) ) (otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) ) )? ) ;
    public final EObject ruleOnMessage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Enumerator lv_state_6_0 = null;

        EObject lv_duration_8_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:1055:2: ( (otherlv_0= 'on' otherlv_1= 'message' otherlv_2= 'from' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' otherlv_5= 'turn' ( (lv_state_6_0= ruleState ) ) (otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) ) )? ) )
            // InternalElixirOfThings.g:1056:2: (otherlv_0= 'on' otherlv_1= 'message' otherlv_2= 'from' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' otherlv_5= 'turn' ( (lv_state_6_0= ruleState ) ) (otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) ) )? )
            {
            // InternalElixirOfThings.g:1056:2: (otherlv_0= 'on' otherlv_1= 'message' otherlv_2= 'from' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' otherlv_5= 'turn' ( (lv_state_6_0= ruleState ) ) (otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) ) )? )
            // InternalElixirOfThings.g:1057:3: otherlv_0= 'on' otherlv_1= 'message' otherlv_2= 'from' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' otherlv_5= 'turn' ( (lv_state_6_0= ruleState ) ) (otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) ) )?
            {
            otherlv_0=(Token)match(input,35,FOLLOW_36); 

            			newLeafNode(otherlv_0, grammarAccess.getOnMessageAccess().getOnKeyword_0());
            		
            otherlv_1=(Token)match(input,36,FOLLOW_37); 

            			newLeafNode(otherlv_1, grammarAccess.getOnMessageAccess().getMessageKeyword_1());
            		
            otherlv_2=(Token)match(input,37,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getOnMessageAccess().getFromKeyword_2());
            		
            // InternalElixirOfThings.g:1069:3: ( (otherlv_3= RULE_ID ) )
            // InternalElixirOfThings.g:1070:4: (otherlv_3= RULE_ID )
            {
            // InternalElixirOfThings.g:1070:4: (otherlv_3= RULE_ID )
            // InternalElixirOfThings.g:1071:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOnMessageRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(otherlv_3, grammarAccess.getOnMessageAccess().getTopicTopicCrossReference_3_0());
            				

            }


            }

            otherlv_4=(Token)match(input,30,FOLLOW_38); 

            			newLeafNode(otherlv_4, grammarAccess.getOnMessageAccess().getColonKeyword_4());
            		
            otherlv_5=(Token)match(input,38,FOLLOW_39); 

            			newLeafNode(otherlv_5, grammarAccess.getOnMessageAccess().getTurnKeyword_5());
            		
            // InternalElixirOfThings.g:1090:3: ( (lv_state_6_0= ruleState ) )
            // InternalElixirOfThings.g:1091:4: (lv_state_6_0= ruleState )
            {
            // InternalElixirOfThings.g:1091:4: (lv_state_6_0= ruleState )
            // InternalElixirOfThings.g:1092:5: lv_state_6_0= ruleState
            {

            					newCompositeNode(grammarAccess.getOnMessageAccess().getStateStateEnumRuleCall_6_0());
            				
            pushFollow(FOLLOW_40);
            lv_state_6_0=ruleState();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOnMessageRule());
            					}
            					set(
            						current,
            						"state",
            						lv_state_6_0,
            						"elixir.of.things.ElixirOfThings.State");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalElixirOfThings.g:1109:3: (otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==39) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalElixirOfThings.g:1110:4: otherlv_7= 'for' ( (lv_duration_8_0= ruleDuration ) )
                    {
                    otherlv_7=(Token)match(input,39,FOLLOW_12); 

                    				newLeafNode(otherlv_7, grammarAccess.getOnMessageAccess().getForKeyword_7_0());
                    			
                    // InternalElixirOfThings.g:1114:4: ( (lv_duration_8_0= ruleDuration ) )
                    // InternalElixirOfThings.g:1115:5: (lv_duration_8_0= ruleDuration )
                    {
                    // InternalElixirOfThings.g:1115:5: (lv_duration_8_0= ruleDuration )
                    // InternalElixirOfThings.g:1116:6: lv_duration_8_0= ruleDuration
                    {

                    						newCompositeNode(grammarAccess.getOnMessageAccess().getDurationDurationParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_duration_8_0=ruleDuration();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOnMessageRule());
                    						}
                    						set(
                    							current,
                    							"duration",
                    							lv_duration_8_0,
                    							"elixir.of.things.ElixirOfThings.Duration");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOnMessage"


    // $ANTLR start "entryRuleCoordinator"
    // InternalElixirOfThings.g:1138:1: entryRuleCoordinator returns [EObject current=null] : iv_ruleCoordinator= ruleCoordinator EOF ;
    public final EObject entryRuleCoordinator() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCoordinator = null;


        try {
            // InternalElixirOfThings.g:1138:52: (iv_ruleCoordinator= ruleCoordinator EOF )
            // InternalElixirOfThings.g:1139:2: iv_ruleCoordinator= ruleCoordinator EOF
            {
             newCompositeNode(grammarAccess.getCoordinatorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCoordinator=ruleCoordinator();

            state._fsp--;

             current =iv_ruleCoordinator; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCoordinator"


    // $ANTLR start "ruleCoordinator"
    // InternalElixirOfThings.g:1145:1: ruleCoordinator returns [EObject current=null] : (otherlv_0= 'coordinator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'deployedOn:' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'subscribeTo:' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ( (lv_rules_9_0= ruleRule ) )* otherlv_10= '}' ) ;
    public final EObject ruleCoordinator() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_rules_9_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:1151:2: ( (otherlv_0= 'coordinator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'deployedOn:' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'subscribeTo:' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ( (lv_rules_9_0= ruleRule ) )* otherlv_10= '}' ) )
            // InternalElixirOfThings.g:1152:2: (otherlv_0= 'coordinator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'deployedOn:' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'subscribeTo:' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ( (lv_rules_9_0= ruleRule ) )* otherlv_10= '}' )
            {
            // InternalElixirOfThings.g:1152:2: (otherlv_0= 'coordinator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'deployedOn:' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'subscribeTo:' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ( (lv_rules_9_0= ruleRule ) )* otherlv_10= '}' )
            // InternalElixirOfThings.g:1153:3: otherlv_0= 'coordinator' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'deployedOn:' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'subscribeTo:' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ( (lv_rules_9_0= ruleRule ) )* otherlv_10= '}'
            {
            otherlv_0=(Token)match(input,40,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getCoordinatorAccess().getCoordinatorKeyword_0());
            		
            // InternalElixirOfThings.g:1157:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:1158:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:1158:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:1159:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getCoordinatorAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCoordinatorRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getCoordinatorAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,26,FOLLOW_3); 

            			newLeafNode(otherlv_3, grammarAccess.getCoordinatorAccess().getDeployedOnKeyword_3());
            		
            // InternalElixirOfThings.g:1183:3: ( (otherlv_4= RULE_ID ) )
            // InternalElixirOfThings.g:1184:4: (otherlv_4= RULE_ID )
            {
            // InternalElixirOfThings.g:1184:4: (otherlv_4= RULE_ID )
            // InternalElixirOfThings.g:1185:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCoordinatorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_33); 

            					newLeafNode(otherlv_4, grammarAccess.getCoordinatorAccess().getDeployedOnNodeCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,34,FOLLOW_3); 

            			newLeafNode(otherlv_5, grammarAccess.getCoordinatorAccess().getSubscribeToKeyword_5());
            		
            // InternalElixirOfThings.g:1200:3: ( (otherlv_6= RULE_ID ) )
            // InternalElixirOfThings.g:1201:4: (otherlv_6= RULE_ID )
            {
            // InternalElixirOfThings.g:1201:4: (otherlv_6= RULE_ID )
            // InternalElixirOfThings.g:1202:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCoordinatorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_41); 

            					newLeafNode(otherlv_6, grammarAccess.getCoordinatorAccess().getSubscribeToTopicCrossReference_6_0());
            				

            }


            }

            // InternalElixirOfThings.g:1213:3: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==20) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalElixirOfThings.g:1214:4: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    {
            	    otherlv_7=(Token)match(input,20,FOLLOW_3); 

            	    				newLeafNode(otherlv_7, grammarAccess.getCoordinatorAccess().getCommaKeyword_7_0());
            	    			
            	    // InternalElixirOfThings.g:1218:4: ( (otherlv_8= RULE_ID ) )
            	    // InternalElixirOfThings.g:1219:5: (otherlv_8= RULE_ID )
            	    {
            	    // InternalElixirOfThings.g:1219:5: (otherlv_8= RULE_ID )
            	    // InternalElixirOfThings.g:1220:6: otherlv_8= RULE_ID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getCoordinatorRule());
            	    						}
            	    					
            	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_41); 

            	    						newLeafNode(otherlv_8, grammarAccess.getCoordinatorAccess().getSubscribeToTopicCrossReference_7_1_0());
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // InternalElixirOfThings.g:1232:3: ( (lv_rules_9_0= ruleRule ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==41) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalElixirOfThings.g:1233:4: (lv_rules_9_0= ruleRule )
            	    {
            	    // InternalElixirOfThings.g:1233:4: (lv_rules_9_0= ruleRule )
            	    // InternalElixirOfThings.g:1234:5: lv_rules_9_0= ruleRule
            	    {

            	    					newCompositeNode(grammarAccess.getCoordinatorAccess().getRulesRuleParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_42);
            	    lv_rules_9_0=ruleRule();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getCoordinatorRule());
            	    					}
            	    					add(
            	    						current,
            	    						"rules",
            	    						lv_rules_9_0,
            	    						"elixir.of.things.ElixirOfThings.Rule");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            otherlv_10=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getCoordinatorAccess().getRightCurlyBracketKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCoordinator"


    // $ANTLR start "entryRuleRule"
    // InternalElixirOfThings.g:1259:1: entryRuleRule returns [EObject current=null] : iv_ruleRule= ruleRule EOF ;
    public final EObject entryRuleRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRule = null;


        try {
            // InternalElixirOfThings.g:1259:45: (iv_ruleRule= ruleRule EOF )
            // InternalElixirOfThings.g:1260:2: iv_ruleRule= ruleRule EOF
            {
             newCompositeNode(grammarAccess.getRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRule=ruleRule();

            state._fsp--;

             current =iv_ruleRule; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRule"


    // $ANTLR start "ruleRule"
    // InternalElixirOfThings.g:1266:1: ruleRule returns [EObject current=null] : (otherlv_0= 'rule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleRuleCondition ) ) ( (lv_actions_4_0= ruleRuleAction ) )+ otherlv_5= '}' ) ;
    public final EObject ruleRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_condition_3_0 = null;

        EObject lv_actions_4_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:1272:2: ( (otherlv_0= 'rule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleRuleCondition ) ) ( (lv_actions_4_0= ruleRuleAction ) )+ otherlv_5= '}' ) )
            // InternalElixirOfThings.g:1273:2: (otherlv_0= 'rule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleRuleCondition ) ) ( (lv_actions_4_0= ruleRuleAction ) )+ otherlv_5= '}' )
            {
            // InternalElixirOfThings.g:1273:2: (otherlv_0= 'rule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleRuleCondition ) ) ( (lv_actions_4_0= ruleRuleAction ) )+ otherlv_5= '}' )
            // InternalElixirOfThings.g:1274:3: otherlv_0= 'rule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_condition_3_0= ruleRuleCondition ) ) ( (lv_actions_4_0= ruleRuleAction ) )+ otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,41,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getRuleAccess().getRuleKeyword_0());
            		
            // InternalElixirOfThings.g:1278:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalElixirOfThings.g:1279:4: (lv_name_1_0= RULE_ID )
            {
            // InternalElixirOfThings.g:1279:4: (lv_name_1_0= RULE_ID )
            // InternalElixirOfThings.g:1280:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getRuleAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRuleRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_24); 

            			newLeafNode(otherlv_2, grammarAccess.getRuleAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalElixirOfThings.g:1300:3: ( (lv_condition_3_0= ruleRuleCondition ) )
            // InternalElixirOfThings.g:1301:4: (lv_condition_3_0= ruleRuleCondition )
            {
            // InternalElixirOfThings.g:1301:4: (lv_condition_3_0= ruleRuleCondition )
            // InternalElixirOfThings.g:1302:5: lv_condition_3_0= ruleRuleCondition
            {

            					newCompositeNode(grammarAccess.getRuleAccess().getConditionRuleConditionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_25);
            lv_condition_3_0=ruleRuleCondition();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRuleRule());
            					}
            					set(
            						current,
            						"condition",
            						lv_condition_3_0,
            						"elixir.of.things.ElixirOfThings.RuleCondition");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalElixirOfThings.g:1319:3: ( (lv_actions_4_0= ruleRuleAction ) )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==31) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalElixirOfThings.g:1320:4: (lv_actions_4_0= ruleRuleAction )
            	    {
            	    // InternalElixirOfThings.g:1320:4: (lv_actions_4_0= ruleRuleAction )
            	    // InternalElixirOfThings.g:1321:5: lv_actions_4_0= ruleRuleAction
            	    {

            	    					newCompositeNode(grammarAccess.getRuleAccess().getActionsRuleActionParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_26);
            	    lv_actions_4_0=ruleRuleAction();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRuleRule());
            	    					}
            	    					add(
            	    						current,
            	    						"actions",
            	    						lv_actions_4_0,
            	    						"elixir.of.things.ElixirOfThings.RuleAction");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);

            otherlv_5=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getRuleAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRule"


    // $ANTLR start "entryRuleRuleCondition"
    // InternalElixirOfThings.g:1346:1: entryRuleRuleCondition returns [EObject current=null] : iv_ruleRuleCondition= ruleRuleCondition EOF ;
    public final EObject entryRuleRuleCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRuleCondition = null;


        try {
            // InternalElixirOfThings.g:1346:54: (iv_ruleRuleCondition= ruleRuleCondition EOF )
            // InternalElixirOfThings.g:1347:2: iv_ruleRuleCondition= ruleRuleCondition EOF
            {
             newCompositeNode(grammarAccess.getRuleConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRuleCondition=ruleRuleCondition();

            state._fsp--;

             current =iv_ruleRuleCondition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRuleCondition"


    // $ANTLR start "ruleRuleCondition"
    // InternalElixirOfThings.g:1353:1: ruleRuleCondition returns [EObject current=null] : (otherlv_0= 'when' ( (otherlv_1= RULE_ID ) ) ( ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ':' ) ;
    public final EObject ruleRuleCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Enumerator lv_operators_2_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:1359:2: ( (otherlv_0= 'when' ( (otherlv_1= RULE_ID ) ) ( ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ':' ) )
            // InternalElixirOfThings.g:1360:2: (otherlv_0= 'when' ( (otherlv_1= RULE_ID ) ) ( ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ':' )
            {
            // InternalElixirOfThings.g:1360:2: (otherlv_0= 'when' ( (otherlv_1= RULE_ID ) ) ( ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ':' )
            // InternalElixirOfThings.g:1361:3: otherlv_0= 'when' ( (otherlv_1= RULE_ID ) ) ( ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ':'
            {
            otherlv_0=(Token)match(input,28,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getRuleConditionAccess().getWhenKeyword_0());
            		
            // InternalElixirOfThings.g:1365:3: ( (otherlv_1= RULE_ID ) )
            // InternalElixirOfThings.g:1366:4: (otherlv_1= RULE_ID )
            {
            // InternalElixirOfThings.g:1366:4: (otherlv_1= RULE_ID )
            // InternalElixirOfThings.g:1367:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRuleConditionRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_43); 

            					newLeafNode(otherlv_1, grammarAccess.getRuleConditionAccess().getTopicsTopicCrossReference_1_0());
            				

            }


            }

            // InternalElixirOfThings.g:1378:3: ( ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=61 && LA14_0<=62)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalElixirOfThings.g:1379:4: ( (lv_operators_2_0= ruleLogicalOp ) ) ( (otherlv_3= RULE_ID ) )
            	    {
            	    // InternalElixirOfThings.g:1379:4: ( (lv_operators_2_0= ruleLogicalOp ) )
            	    // InternalElixirOfThings.g:1380:5: (lv_operators_2_0= ruleLogicalOp )
            	    {
            	    // InternalElixirOfThings.g:1380:5: (lv_operators_2_0= ruleLogicalOp )
            	    // InternalElixirOfThings.g:1381:6: lv_operators_2_0= ruleLogicalOp
            	    {

            	    						newCompositeNode(grammarAccess.getRuleConditionAccess().getOperatorsLogicalOpEnumRuleCall_2_0_0());
            	    					
            	    pushFollow(FOLLOW_3);
            	    lv_operators_2_0=ruleLogicalOp();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getRuleConditionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"operators",
            	    							lv_operators_2_0,
            	    							"elixir.of.things.ElixirOfThings.LogicalOp");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalElixirOfThings.g:1398:4: ( (otherlv_3= RULE_ID ) )
            	    // InternalElixirOfThings.g:1399:5: (otherlv_3= RULE_ID )
            	    {
            	    // InternalElixirOfThings.g:1399:5: (otherlv_3= RULE_ID )
            	    // InternalElixirOfThings.g:1400:6: otherlv_3= RULE_ID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getRuleConditionRule());
            	    						}
            	    					
            	    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_43); 

            	    						newLeafNode(otherlv_3, grammarAccess.getRuleConditionAccess().getTopicsTopicCrossReference_2_1_0());
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            otherlv_4=(Token)match(input,30,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getRuleConditionAccess().getColonKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRuleCondition"


    // $ANTLR start "entryRuleRuleAction"
    // InternalElixirOfThings.g:1420:1: entryRuleRuleAction returns [EObject current=null] : iv_ruleRuleAction= ruleRuleAction EOF ;
    public final EObject entryRuleRuleAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRuleAction = null;


        try {
            // InternalElixirOfThings.g:1420:51: (iv_ruleRuleAction= ruleRuleAction EOF )
            // InternalElixirOfThings.g:1421:2: iv_ruleRuleAction= ruleRuleAction EOF
            {
             newCompositeNode(grammarAccess.getRuleActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRuleAction=ruleRuleAction();

            state._fsp--;

             current =iv_ruleRuleAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRuleAction"


    // $ANTLR start "ruleRuleAction"
    // InternalElixirOfThings.g:1427:1: ruleRuleAction returns [EObject current=null] : (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' (otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) ) )? otherlv_6= '}' ) ;
    public final EObject ruleRuleAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_message_5_0=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1433:2: ( (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' (otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) ) )? otherlv_6= '}' ) )
            // InternalElixirOfThings.g:1434:2: (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' (otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) ) )? otherlv_6= '}' )
            {
            // InternalElixirOfThings.g:1434:2: (otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' (otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) ) )? otherlv_6= '}' )
            // InternalElixirOfThings.g:1435:3: otherlv_0= 'publish' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' otherlv_3= '{' (otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) ) )? otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,31,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getRuleActionAccess().getPublishKeyword_0());
            		
            // InternalElixirOfThings.g:1439:3: ( (otherlv_1= RULE_ID ) )
            // InternalElixirOfThings.g:1440:4: (otherlv_1= RULE_ID )
            {
            // InternalElixirOfThings.g:1440:4: (otherlv_1= RULE_ID )
            // InternalElixirOfThings.g:1441:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRuleActionRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(otherlv_1, grammarAccess.getRuleActionAccess().getTopicTopicCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,30,FOLLOW_4); 

            			newLeafNode(otherlv_2, grammarAccess.getRuleActionAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,12,FOLLOW_44); 

            			newLeafNode(otherlv_3, grammarAccess.getRuleActionAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalElixirOfThings.g:1460:3: (otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==42) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalElixirOfThings.g:1461:4: otherlv_4= 'message:' ( (lv_message_5_0= RULE_STRING ) )
                    {
                    otherlv_4=(Token)match(input,42,FOLLOW_10); 

                    				newLeafNode(otherlv_4, grammarAccess.getRuleActionAccess().getMessageKeyword_4_0());
                    			
                    // InternalElixirOfThings.g:1465:4: ( (lv_message_5_0= RULE_STRING ) )
                    // InternalElixirOfThings.g:1466:5: (lv_message_5_0= RULE_STRING )
                    {
                    // InternalElixirOfThings.g:1466:5: (lv_message_5_0= RULE_STRING )
                    // InternalElixirOfThings.g:1467:6: lv_message_5_0= RULE_STRING
                    {
                    lv_message_5_0=(Token)match(input,RULE_STRING,FOLLOW_45); 

                    						newLeafNode(lv_message_5_0, grammarAccess.getRuleActionAccess().getMessageSTRINGTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRuleActionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"message",
                    							lv_message_5_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,13,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getRuleActionAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRuleAction"


    // $ANTLR start "entryRuleDuration"
    // InternalElixirOfThings.g:1492:1: entryRuleDuration returns [EObject current=null] : iv_ruleDuration= ruleDuration EOF ;
    public final EObject entryRuleDuration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDuration = null;


        try {
            // InternalElixirOfThings.g:1492:49: (iv_ruleDuration= ruleDuration EOF )
            // InternalElixirOfThings.g:1493:2: iv_ruleDuration= ruleDuration EOF
            {
             newCompositeNode(grammarAccess.getDurationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDuration=ruleDuration();

            state._fsp--;

             current =iv_ruleDuration; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDuration"


    // $ANTLR start "ruleDuration"
    // InternalElixirOfThings.g:1499:1: ruleDuration returns [EObject current=null] : ( ( (lv_value_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) ) ;
    public final EObject ruleDuration() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;
        Enumerator lv_unit_1_0 = null;



        	enterRule();

        try {
            // InternalElixirOfThings.g:1505:2: ( ( ( (lv_value_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) ) )
            // InternalElixirOfThings.g:1506:2: ( ( (lv_value_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) )
            {
            // InternalElixirOfThings.g:1506:2: ( ( (lv_value_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) )
            // InternalElixirOfThings.g:1507:3: ( (lv_value_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) )
            {
            // InternalElixirOfThings.g:1507:3: ( (lv_value_0_0= RULE_INT ) )
            // InternalElixirOfThings.g:1508:4: (lv_value_0_0= RULE_INT )
            {
            // InternalElixirOfThings.g:1508:4: (lv_value_0_0= RULE_INT )
            // InternalElixirOfThings.g:1509:5: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_46); 

            					newLeafNode(lv_value_0_0, grammarAccess.getDurationAccess().getValueINTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDurationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_0_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalElixirOfThings.g:1525:3: ( (lv_unit_1_0= ruleTimeUnit ) )
            // InternalElixirOfThings.g:1526:4: (lv_unit_1_0= ruleTimeUnit )
            {
            // InternalElixirOfThings.g:1526:4: (lv_unit_1_0= ruleTimeUnit )
            // InternalElixirOfThings.g:1527:5: lv_unit_1_0= ruleTimeUnit
            {

            					newCompositeNode(grammarAccess.getDurationAccess().getUnitTimeUnitEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_unit_1_0=ruleTimeUnit();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDurationRule());
            					}
            					set(
            						current,
            						"unit",
            						lv_unit_1_0,
            						"elixir.of.things.ElixirOfThings.TimeUnit");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDuration"


    // $ANTLR start "ruleSensorType"
    // InternalElixirOfThings.g:1548:1: ruleSensorType returns [Enumerator current=null] : ( (enumLiteral_0= 'TEMP_DS18B20' ) | (enumLiteral_1= 'TEMP_DHT22' ) | (enumLiteral_2= 'HUMIDITY' ) | (enumLiteral_3= 'MOTION_PIR' ) | (enumLiteral_4= 'LIGHT' ) | (enumLiteral_5= 'DISTANCE' ) ) ;
    public final Enumerator ruleSensorType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1554:2: ( ( (enumLiteral_0= 'TEMP_DS18B20' ) | (enumLiteral_1= 'TEMP_DHT22' ) | (enumLiteral_2= 'HUMIDITY' ) | (enumLiteral_3= 'MOTION_PIR' ) | (enumLiteral_4= 'LIGHT' ) | (enumLiteral_5= 'DISTANCE' ) ) )
            // InternalElixirOfThings.g:1555:2: ( (enumLiteral_0= 'TEMP_DS18B20' ) | (enumLiteral_1= 'TEMP_DHT22' ) | (enumLiteral_2= 'HUMIDITY' ) | (enumLiteral_3= 'MOTION_PIR' ) | (enumLiteral_4= 'LIGHT' ) | (enumLiteral_5= 'DISTANCE' ) )
            {
            // InternalElixirOfThings.g:1555:2: ( (enumLiteral_0= 'TEMP_DS18B20' ) | (enumLiteral_1= 'TEMP_DHT22' ) | (enumLiteral_2= 'HUMIDITY' ) | (enumLiteral_3= 'MOTION_PIR' ) | (enumLiteral_4= 'LIGHT' ) | (enumLiteral_5= 'DISTANCE' ) )
            int alt16=6;
            switch ( input.LA(1) ) {
            case 43:
                {
                alt16=1;
                }
                break;
            case 44:
                {
                alt16=2;
                }
                break;
            case 45:
                {
                alt16=3;
                }
                break;
            case 46:
                {
                alt16=4;
                }
                break;
            case 47:
                {
                alt16=5;
                }
                break;
            case 48:
                {
                alt16=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // InternalElixirOfThings.g:1556:3: (enumLiteral_0= 'TEMP_DS18B20' )
                    {
                    // InternalElixirOfThings.g:1556:3: (enumLiteral_0= 'TEMP_DS18B20' )
                    // InternalElixirOfThings.g:1557:4: enumLiteral_0= 'TEMP_DS18B20'
                    {
                    enumLiteral_0=(Token)match(input,43,FOLLOW_2); 

                    				current = grammarAccess.getSensorTypeAccess().getTEMP_DS18B20EnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSensorTypeAccess().getTEMP_DS18B20EnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1564:3: (enumLiteral_1= 'TEMP_DHT22' )
                    {
                    // InternalElixirOfThings.g:1564:3: (enumLiteral_1= 'TEMP_DHT22' )
                    // InternalElixirOfThings.g:1565:4: enumLiteral_1= 'TEMP_DHT22'
                    {
                    enumLiteral_1=(Token)match(input,44,FOLLOW_2); 

                    				current = grammarAccess.getSensorTypeAccess().getTEMP_DHT22EnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getSensorTypeAccess().getTEMP_DHT22EnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalElixirOfThings.g:1572:3: (enumLiteral_2= 'HUMIDITY' )
                    {
                    // InternalElixirOfThings.g:1572:3: (enumLiteral_2= 'HUMIDITY' )
                    // InternalElixirOfThings.g:1573:4: enumLiteral_2= 'HUMIDITY'
                    {
                    enumLiteral_2=(Token)match(input,45,FOLLOW_2); 

                    				current = grammarAccess.getSensorTypeAccess().getHUMIDITYEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getSensorTypeAccess().getHUMIDITYEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalElixirOfThings.g:1580:3: (enumLiteral_3= 'MOTION_PIR' )
                    {
                    // InternalElixirOfThings.g:1580:3: (enumLiteral_3= 'MOTION_PIR' )
                    // InternalElixirOfThings.g:1581:4: enumLiteral_3= 'MOTION_PIR'
                    {
                    enumLiteral_3=(Token)match(input,46,FOLLOW_2); 

                    				current = grammarAccess.getSensorTypeAccess().getMOTION_PIREnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getSensorTypeAccess().getMOTION_PIREnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalElixirOfThings.g:1588:3: (enumLiteral_4= 'LIGHT' )
                    {
                    // InternalElixirOfThings.g:1588:3: (enumLiteral_4= 'LIGHT' )
                    // InternalElixirOfThings.g:1589:4: enumLiteral_4= 'LIGHT'
                    {
                    enumLiteral_4=(Token)match(input,47,FOLLOW_2); 

                    				current = grammarAccess.getSensorTypeAccess().getLIGHTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getSensorTypeAccess().getLIGHTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalElixirOfThings.g:1596:3: (enumLiteral_5= 'DISTANCE' )
                    {
                    // InternalElixirOfThings.g:1596:3: (enumLiteral_5= 'DISTANCE' )
                    // InternalElixirOfThings.g:1597:4: enumLiteral_5= 'DISTANCE'
                    {
                    enumLiteral_5=(Token)match(input,48,FOLLOW_2); 

                    				current = grammarAccess.getSensorTypeAccess().getDISTANCEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getSensorTypeAccess().getDISTANCEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSensorType"


    // $ANTLR start "ruleActuatorType"
    // InternalElixirOfThings.g:1607:1: ruleActuatorType returns [Enumerator current=null] : ( (enumLiteral_0= 'RELAY' ) | (enumLiteral_1= 'LED' ) | (enumLiteral_2= 'BUZZER' ) | (enumLiteral_3= 'SERVO' ) ) ;
    public final Enumerator ruleActuatorType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1613:2: ( ( (enumLiteral_0= 'RELAY' ) | (enumLiteral_1= 'LED' ) | (enumLiteral_2= 'BUZZER' ) | (enumLiteral_3= 'SERVO' ) ) )
            // InternalElixirOfThings.g:1614:2: ( (enumLiteral_0= 'RELAY' ) | (enumLiteral_1= 'LED' ) | (enumLiteral_2= 'BUZZER' ) | (enumLiteral_3= 'SERVO' ) )
            {
            // InternalElixirOfThings.g:1614:2: ( (enumLiteral_0= 'RELAY' ) | (enumLiteral_1= 'LED' ) | (enumLiteral_2= 'BUZZER' ) | (enumLiteral_3= 'SERVO' ) )
            int alt17=4;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt17=1;
                }
                break;
            case 50:
                {
                alt17=2;
                }
                break;
            case 51:
                {
                alt17=3;
                }
                break;
            case 52:
                {
                alt17=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalElixirOfThings.g:1615:3: (enumLiteral_0= 'RELAY' )
                    {
                    // InternalElixirOfThings.g:1615:3: (enumLiteral_0= 'RELAY' )
                    // InternalElixirOfThings.g:1616:4: enumLiteral_0= 'RELAY'
                    {
                    enumLiteral_0=(Token)match(input,49,FOLLOW_2); 

                    				current = grammarAccess.getActuatorTypeAccess().getRELAYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getActuatorTypeAccess().getRELAYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1623:3: (enumLiteral_1= 'LED' )
                    {
                    // InternalElixirOfThings.g:1623:3: (enumLiteral_1= 'LED' )
                    // InternalElixirOfThings.g:1624:4: enumLiteral_1= 'LED'
                    {
                    enumLiteral_1=(Token)match(input,50,FOLLOW_2); 

                    				current = grammarAccess.getActuatorTypeAccess().getLEDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getActuatorTypeAccess().getLEDEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalElixirOfThings.g:1631:3: (enumLiteral_2= 'BUZZER' )
                    {
                    // InternalElixirOfThings.g:1631:3: (enumLiteral_2= 'BUZZER' )
                    // InternalElixirOfThings.g:1632:4: enumLiteral_2= 'BUZZER'
                    {
                    enumLiteral_2=(Token)match(input,51,FOLLOW_2); 

                    				current = grammarAccess.getActuatorTypeAccess().getBUZZEREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getActuatorTypeAccess().getBUZZEREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalElixirOfThings.g:1639:3: (enumLiteral_3= 'SERVO' )
                    {
                    // InternalElixirOfThings.g:1639:3: (enumLiteral_3= 'SERVO' )
                    // InternalElixirOfThings.g:1640:4: enumLiteral_3= 'SERVO'
                    {
                    enumLiteral_3=(Token)match(input,52,FOLLOW_2); 

                    				current = grammarAccess.getActuatorTypeAccess().getSERVOEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getActuatorTypeAccess().getSERVOEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActuatorType"


    // $ANTLR start "ruleQoS"
    // InternalElixirOfThings.g:1650:1: ruleQoS returns [Enumerator current=null] : ( (enumLiteral_0= 'AT_MOST_ONCE' ) | (enumLiteral_1= 'AT_LEAST_ONCE' ) | (enumLiteral_2= 'EXACTLY_ONCE' ) ) ;
    public final Enumerator ruleQoS() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1656:2: ( ( (enumLiteral_0= 'AT_MOST_ONCE' ) | (enumLiteral_1= 'AT_LEAST_ONCE' ) | (enumLiteral_2= 'EXACTLY_ONCE' ) ) )
            // InternalElixirOfThings.g:1657:2: ( (enumLiteral_0= 'AT_MOST_ONCE' ) | (enumLiteral_1= 'AT_LEAST_ONCE' ) | (enumLiteral_2= 'EXACTLY_ONCE' ) )
            {
            // InternalElixirOfThings.g:1657:2: ( (enumLiteral_0= 'AT_MOST_ONCE' ) | (enumLiteral_1= 'AT_LEAST_ONCE' ) | (enumLiteral_2= 'EXACTLY_ONCE' ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case 53:
                {
                alt18=1;
                }
                break;
            case 54:
                {
                alt18=2;
                }
                break;
            case 55:
                {
                alt18=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalElixirOfThings.g:1658:3: (enumLiteral_0= 'AT_MOST_ONCE' )
                    {
                    // InternalElixirOfThings.g:1658:3: (enumLiteral_0= 'AT_MOST_ONCE' )
                    // InternalElixirOfThings.g:1659:4: enumLiteral_0= 'AT_MOST_ONCE'
                    {
                    enumLiteral_0=(Token)match(input,53,FOLLOW_2); 

                    				current = grammarAccess.getQoSAccess().getAT_MOST_ONCEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getQoSAccess().getAT_MOST_ONCEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1666:3: (enumLiteral_1= 'AT_LEAST_ONCE' )
                    {
                    // InternalElixirOfThings.g:1666:3: (enumLiteral_1= 'AT_LEAST_ONCE' )
                    // InternalElixirOfThings.g:1667:4: enumLiteral_1= 'AT_LEAST_ONCE'
                    {
                    enumLiteral_1=(Token)match(input,54,FOLLOW_2); 

                    				current = grammarAccess.getQoSAccess().getAT_LEAST_ONCEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getQoSAccess().getAT_LEAST_ONCEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalElixirOfThings.g:1674:3: (enumLiteral_2= 'EXACTLY_ONCE' )
                    {
                    // InternalElixirOfThings.g:1674:3: (enumLiteral_2= 'EXACTLY_ONCE' )
                    // InternalElixirOfThings.g:1675:4: enumLiteral_2= 'EXACTLY_ONCE'
                    {
                    enumLiteral_2=(Token)match(input,55,FOLLOW_2); 

                    				current = grammarAccess.getQoSAccess().getEXACTLY_ONCEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getQoSAccess().getEXACTLY_ONCEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQoS"


    // $ANTLR start "ruleOperator"
    // InternalElixirOfThings.g:1685:1: ruleOperator returns [Enumerator current=null] : ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) ) ;
    public final Enumerator ruleOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1691:2: ( ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) ) )
            // InternalElixirOfThings.g:1692:2: ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) )
            {
            // InternalElixirOfThings.g:1692:2: ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) )
            int alt19=5;
            switch ( input.LA(1) ) {
            case 56:
                {
                alt19=1;
                }
                break;
            case 57:
                {
                alt19=2;
                }
                break;
            case 58:
                {
                alt19=3;
                }
                break;
            case 59:
                {
                alt19=4;
                }
                break;
            case 60:
                {
                alt19=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalElixirOfThings.g:1693:3: (enumLiteral_0= '>' )
                    {
                    // InternalElixirOfThings.g:1693:3: (enumLiteral_0= '>' )
                    // InternalElixirOfThings.g:1694:4: enumLiteral_0= '>'
                    {
                    enumLiteral_0=(Token)match(input,56,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGREATER_THANEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getOperatorAccess().getGREATER_THANEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1701:3: (enumLiteral_1= '<' )
                    {
                    // InternalElixirOfThings.g:1701:3: (enumLiteral_1= '<' )
                    // InternalElixirOfThings.g:1702:4: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,57,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLESS_THANEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getOperatorAccess().getLESS_THANEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalElixirOfThings.g:1709:3: (enumLiteral_2= '>=' )
                    {
                    // InternalElixirOfThings.g:1709:3: (enumLiteral_2= '>=' )
                    // InternalElixirOfThings.g:1710:4: enumLiteral_2= '>='
                    {
                    enumLiteral_2=(Token)match(input,58,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGREATER_EQEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getOperatorAccess().getGREATER_EQEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalElixirOfThings.g:1717:3: (enumLiteral_3= '<=' )
                    {
                    // InternalElixirOfThings.g:1717:3: (enumLiteral_3= '<=' )
                    // InternalElixirOfThings.g:1718:4: enumLiteral_3= '<='
                    {
                    enumLiteral_3=(Token)match(input,59,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLESS_EQEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getOperatorAccess().getLESS_EQEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalElixirOfThings.g:1725:3: (enumLiteral_4= '==' )
                    {
                    // InternalElixirOfThings.g:1725:3: (enumLiteral_4= '==' )
                    // InternalElixirOfThings.g:1726:4: enumLiteral_4= '=='
                    {
                    enumLiteral_4=(Token)match(input,60,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getEQUALSEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getOperatorAccess().getEQUALSEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperator"


    // $ANTLR start "ruleLogicalOp"
    // InternalElixirOfThings.g:1736:1: ruleLogicalOp returns [Enumerator current=null] : ( (enumLiteral_0= 'and' ) | (enumLiteral_1= 'or' ) ) ;
    public final Enumerator ruleLogicalOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1742:2: ( ( (enumLiteral_0= 'and' ) | (enumLiteral_1= 'or' ) ) )
            // InternalElixirOfThings.g:1743:2: ( (enumLiteral_0= 'and' ) | (enumLiteral_1= 'or' ) )
            {
            // InternalElixirOfThings.g:1743:2: ( (enumLiteral_0= 'and' ) | (enumLiteral_1= 'or' ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==61) ) {
                alt20=1;
            }
            else if ( (LA20_0==62) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalElixirOfThings.g:1744:3: (enumLiteral_0= 'and' )
                    {
                    // InternalElixirOfThings.g:1744:3: (enumLiteral_0= 'and' )
                    // InternalElixirOfThings.g:1745:4: enumLiteral_0= 'and'
                    {
                    enumLiteral_0=(Token)match(input,61,FOLLOW_2); 

                    				current = grammarAccess.getLogicalOpAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLogicalOpAccess().getANDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1752:3: (enumLiteral_1= 'or' )
                    {
                    // InternalElixirOfThings.g:1752:3: (enumLiteral_1= 'or' )
                    // InternalElixirOfThings.g:1753:4: enumLiteral_1= 'or'
                    {
                    enumLiteral_1=(Token)match(input,62,FOLLOW_2); 

                    				current = grammarAccess.getLogicalOpAccess().getOREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLogicalOpAccess().getOREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLogicalOp"


    // $ANTLR start "ruleState"
    // InternalElixirOfThings.g:1763:1: ruleState returns [Enumerator current=null] : ( (enumLiteral_0= 'ON' ) | (enumLiteral_1= 'OFF' ) ) ;
    public final Enumerator ruleState() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1769:2: ( ( (enumLiteral_0= 'ON' ) | (enumLiteral_1= 'OFF' ) ) )
            // InternalElixirOfThings.g:1770:2: ( (enumLiteral_0= 'ON' ) | (enumLiteral_1= 'OFF' ) )
            {
            // InternalElixirOfThings.g:1770:2: ( (enumLiteral_0= 'ON' ) | (enumLiteral_1= 'OFF' ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==63) ) {
                alt21=1;
            }
            else if ( (LA21_0==64) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalElixirOfThings.g:1771:3: (enumLiteral_0= 'ON' )
                    {
                    // InternalElixirOfThings.g:1771:3: (enumLiteral_0= 'ON' )
                    // InternalElixirOfThings.g:1772:4: enumLiteral_0= 'ON'
                    {
                    enumLiteral_0=(Token)match(input,63,FOLLOW_2); 

                    				current = grammarAccess.getStateAccess().getONEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getStateAccess().getONEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1779:3: (enumLiteral_1= 'OFF' )
                    {
                    // InternalElixirOfThings.g:1779:3: (enumLiteral_1= 'OFF' )
                    // InternalElixirOfThings.g:1780:4: enumLiteral_1= 'OFF'
                    {
                    enumLiteral_1=(Token)match(input,64,FOLLOW_2); 

                    				current = grammarAccess.getStateAccess().getOFFEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getStateAccess().getOFFEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleState"


    // $ANTLR start "ruleTimeUnit"
    // InternalElixirOfThings.g:1790:1: ruleTimeUnit returns [Enumerator current=null] : ( (enumLiteral_0= 'sec' ) | (enumLiteral_1= 'min' ) | (enumLiteral_2= 'ms' ) ) ;
    public final Enumerator ruleTimeUnit() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalElixirOfThings.g:1796:2: ( ( (enumLiteral_0= 'sec' ) | (enumLiteral_1= 'min' ) | (enumLiteral_2= 'ms' ) ) )
            // InternalElixirOfThings.g:1797:2: ( (enumLiteral_0= 'sec' ) | (enumLiteral_1= 'min' ) | (enumLiteral_2= 'ms' ) )
            {
            // InternalElixirOfThings.g:1797:2: ( (enumLiteral_0= 'sec' ) | (enumLiteral_1= 'min' ) | (enumLiteral_2= 'ms' ) )
            int alt22=3;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt22=1;
                }
                break;
            case 66:
                {
                alt22=2;
                }
                break;
            case 67:
                {
                alt22=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // InternalElixirOfThings.g:1798:3: (enumLiteral_0= 'sec' )
                    {
                    // InternalElixirOfThings.g:1798:3: (enumLiteral_0= 'sec' )
                    // InternalElixirOfThings.g:1799:4: enumLiteral_0= 'sec'
                    {
                    enumLiteral_0=(Token)match(input,65,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getSECEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTimeUnitAccess().getSECEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalElixirOfThings.g:1806:3: (enumLiteral_1= 'min' )
                    {
                    // InternalElixirOfThings.g:1806:3: (enumLiteral_1= 'min' )
                    // InternalElixirOfThings.g:1807:4: enumLiteral_1= 'min'
                    {
                    enumLiteral_1=(Token)match(input,66,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getMINEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTimeUnitAccess().getMINEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalElixirOfThings.g:1814:3: (enumLiteral_2= 'ms' )
                    {
                    // InternalElixirOfThings.g:1814:3: (enumLiteral_2= 'ms' )
                    // InternalElixirOfThings.g:1815:4: enumLiteral_2= 'ms'
                    {
                    enumLiteral_2=(Token)match(input,67,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getMSEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTimeUnitAccess().getMSEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTimeUnit"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000010200432000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000010200422000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000010200402000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x00E0000000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0001F80000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000008002000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000080002000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x1F00000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000120000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x001E000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000800102000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000800002000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000020000102000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000020000002000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x6000000040000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000040000002000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x000000000000000EL});

}