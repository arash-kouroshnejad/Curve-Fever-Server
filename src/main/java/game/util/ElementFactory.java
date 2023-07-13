package game.util;

import common.gfx.editor.LevelEditor;
import common.gfx.objects.StaticElement;
import common.net.agent.AbstractAgent;
import common.util.CommandFactory;
import game.model.Player;

public class ElementFactory extends LevelEditor {
    final AbstractAgent server;
    final Player player1;
    final Player player2;

    public ElementFactory(AbstractAgent server, Player player1, Player player2) {
        this.server = server;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void removeElement(StaticElement element) {
        super.removeElement(element);
        server.send(CommandFactory.dropElement(player1.entity(), element.getType()));
        server.send(CommandFactory.dropElement(player2.entity(), element.getType()));
    }

    public void dropLayer(int layerIndex, boolean dynamic) {
        super.dropLayer(layerIndex, dynamic);
        server.send(CommandFactory.dropLayer(player1.entity(), layerIndex));
        server.send(CommandFactory.dropLayer(player2.entity(), layerIndex));
    }
}
