package lexing.ast;

import java.util.ArrayList;
import java.util.List;

public interface Node {

    default void addChildren(List<Node> children) {
        //NO-OP
    }

}