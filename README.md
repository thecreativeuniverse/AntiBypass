# AntiBypass
Developed by creativeuniverse: Iggley#7720 | github.com/thecreativeuniverse

TODO: add stuff to this 

## MyhicMobs examples
### BlockCheck
Item File:
```yaml
TestItem:
  Id: STICK
  Skills:
  - skill:TestSkill @Self ~onUse 
  - skill:messageOnBlockBreak @Self ~onBlockBreak```
```
Skill File:
```yaml
# Use this skill to set variables for future conditions.
TestSkill:
  Cooldown: 1
  Skills:
  # Use %gettargetblock% to get the player's target block
  - setvariable{var=caster.targetBlockLoc;scope=caster;type=STRING;val="%gettargetblock%"} @Self
  - skill:_TestSkillInternal @TargetBlock
_TestSkillInternal:
  TriggerConditions:
  # Use previously saved variable to check permissions on the target block
  - stringequals{val1="<target.var.targetBlockLoc>";val2="NULL"} castinstead clickedNothing
  - stringequals{val1="%blockcheck_<target.var.targetBlockLoc>%";val2="true"} orelsecast noPermission
  Skills:
  - message{m="Success! You are no longer bypassing server protections"} @Self
  - breakblock{forcesync=true}
  #cleanup:
  - unsetvariable{var=caster.targetBlockLoc;scope=caster} @Self
messageOnBlockBreak:
  Skills:
  - message{m="If blockcheck.blockevent is enabled in config, 
    this message will appear both when the player breaks/places 
    (depending on your settings) a block, and also when the blockcheck
    PAPI is used."} @Self
clickedNothing:
  Skills:
  - message{m="You didn't click anything."} @Self
noPermission:
  Skills:
  - message{m="You don't have permission to build here."} @Self
```