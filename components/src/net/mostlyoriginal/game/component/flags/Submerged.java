package net.mostlyoriginal.game.component.flags;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;

/**
 * @author Daan van Yperen
 */
@DelayedComponentRemoval
public class Submerged extends Component {
    public String originalAnim;
    public String submergedAnim = "silhouette";
}
