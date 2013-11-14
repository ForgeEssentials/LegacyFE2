package net.minecraftforge.permissions.api.context;

import java.util.List;

public interface IAreaContext extends IContext
{
    boolean overlapsWith(IAreaContext context);

    boolean contains(IAreaContext area);

    boolean contains(ILocationContext loc);

    List<ILocationContext> getLocations();
}
