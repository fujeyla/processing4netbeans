<#if package?? && package != "">
package ${package};
</#if>

import processing.core.*;

public class ${name} {

    private final PApplet parent;

    public ${name}(PApplet p) {
        parent = p;
    }

    public void draw() {
        parent.rect(0, 0, 20, 20);
    }
    
}
