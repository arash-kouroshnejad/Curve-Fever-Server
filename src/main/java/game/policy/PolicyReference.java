package game.policy;


import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import game.model.Player;
import game.model.Rewards;
import lombok.Getter;
import lombok.Setter;

public class PolicyReference {
    @Getter
    final Player player1;
    @Getter
    final Player player2;
    @Getter @Setter
    DynamicElement player1Character;
    @Getter @Setter
    DynamicElement player2Character;
    @Getter @Setter
    Rewards player1State;
    @Getter @Setter
    Rewards player2State;
    @Getter @Setter
    LevelEditor editor;
    @Getter @Setter
    PowerupPolicy player1Effect;
    @Getter @Setter
    PowerupPolicy player2Effect;

    public PolicyReference(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
