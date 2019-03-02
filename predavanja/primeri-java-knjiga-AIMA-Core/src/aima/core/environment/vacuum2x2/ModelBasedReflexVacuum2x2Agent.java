package aima.core.environment.vacuum2x2;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.Model;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractAgent;
import aima.core.agent.impl.DynamicState;
import aima.core.agent.impl.NoOpAction;
import aima.core.agent.impl.aprog.ModelBasedReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.ANDCondition4;
import aima.core.agent.impl.aprog.simplerule.EQUALCondition;
import aima.core.agent.impl.aprog.simplerule.Rule;

/**
 * @author Ciaran O'Reilly
 * 
 */
public class ModelBasedReflexVacuum2x2Agent extends AbstractAgent
{

  private static final String ATTRIBUTE_CURRENT_LOCATION = "currentLocation";
  private static final String ATTRIBUTE_CURRENT_STATE = "currentState";
  private static final String ATTRIBUTE_STATE_LOCATION_A1 = "stateLocationA1";
  private static final String ATTRIBUTE_STATE_LOCATION_A2 = "stateLocationA2";
  private static final String ATTRIBUTE_STATE_LOCATION_B1 = "stateLocationB1";
  private static final String ATTRIBUTE_STATE_LOCATION_B2 = "stateLocationB2";

  public ModelBasedReflexVacuum2x2Agent()
  {
    super( new ModelBasedReflexAgentProgram()
    {
      @Override
      protected void init()
      {
        setState( new DynamicState() );
        setRules( getRuleSet() );
      }

      protected DynamicState updateState( DynamicState state, Action anAction,
          Percept percept, Model model )
      {

        LocalVacuum2x2EnvironmentPercept vep = ( LocalVacuum2x2EnvironmentPercept ) percept;

        state.setAttribute( ATTRIBUTE_CURRENT_LOCATION, vep.getAgentLocation() );
        state.setAttribute( ATTRIBUTE_CURRENT_STATE, vep.getLocationState() );
        // Keep track of the state of the different locations
        if ( Vacuum2x2Environment.LOKACIJA_A1 == vep.getAgentLocation() )
        {
          state.setAttribute( ATTRIBUTE_STATE_LOCATION_A1,
              vep.getLocationState() );
        } else if ( Vacuum2x2Environment.LOKACIJA_B1 == vep.getAgentLocation() )
        {
          state.setAttribute( ATTRIBUTE_STATE_LOCATION_B1,
              vep.getLocationState() );
        }
        else if ( Vacuum2x2Environment.LOKACIJA_A2 == vep.getAgentLocation() )
        {
          state.setAttribute( ATTRIBUTE_STATE_LOCATION_A2,
              vep.getLocationState() );
        }else if ( Vacuum2x2Environment.LOKACIJA_B2 == vep.getAgentLocation() )
        {
          state.setAttribute( ATTRIBUTE_STATE_LOCATION_B2,
              vep.getLocationState() );
        }
        return state;
      }
    } );
  }

  //
  // PRIVATE METHODS
  //
  private static Set<Rule> getRuleSet()
  {
    // Note: Using a LinkedHashSet so that the iteration order (i.e. implied
    // precedence) of rules can be guaranteed.
    Set<Rule> rules = new LinkedHashSet<Rule>();

    rules.add( new Rule( new ANDCondition4( 
        new EQUALCondition( ATTRIBUTE_STATE_LOCATION_A1, Vacuum2x2Environment.LocationState.Cisto ),
        new EQUALCondition( ATTRIBUTE_STATE_LOCATION_A2, Vacuum2x2Environment.LocationState.Cisto ),
        new EQUALCondition( ATTRIBUTE_STATE_LOCATION_B1, Vacuum2x2Environment.LocationState.Cisto ), 
        new EQUALCondition( ATTRIBUTE_STATE_LOCATION_B2, Vacuum2x2Environment.LocationState.Cisto )) 
    , NoOpAction.NO_OP ) );
    rules.add( new Rule( new EQUALCondition( ATTRIBUTE_CURRENT_STATE,
        Vacuum2x2Environment.LocationState.Prljavo ),
        Vacuum2x2Environment.AKCIJA_USISAVAJ ) );
    rules.add( new Rule( new EQUALCondition( ATTRIBUTE_CURRENT_LOCATION,
        Vacuum2x2Environment.LOKACIJA_A1 ),
        Vacuum2x2Environment.AKCIJA_IDI_DESNO ) );
    rules.add( new Rule( new EQUALCondition( ATTRIBUTE_CURRENT_LOCATION,
        Vacuum2x2Environment.LOKACIJA_A2 ),
        Vacuum2x2Environment.AKCIJA_IDI_DOLE ) );
    rules.add( new Rule( new EQUALCondition( ATTRIBUTE_CURRENT_LOCATION,
        Vacuum2x2Environment.LOKACIJA_B2 ),
        Vacuum2x2Environment.AKCIJA_IDI_LEVO ) );
    rules.add( new Rule( new EQUALCondition( ATTRIBUTE_CURRENT_LOCATION,
        Vacuum2x2Environment.LOKACIJA_B1 ),
        Vacuum2x2Environment.AKCIJA_IDI_GORE ) );

    return rules;
  }
}
