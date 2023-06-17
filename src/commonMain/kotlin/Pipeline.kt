package dev.sitar.keystone

public data class Stage(val name: String)

public typealias Filter<Context> = suspend Context.() -> Unit

public open class Pipeline<Context: Any>(
    public val stages: Set<Stage>,
    // executed after each filter is run. if false is returned then propagation is cancelled
    private val onFilter: (Context) -> Boolean = { true }
) {
    private val filters: MutableMap<Stage, MutableSet<Filter<Context>>> = mutableMapOf()

    public constructor(vararg stages: Stage): this(stages.toSet())

    public fun filter(stage: Stage, filter: Filter<Context>) {
        require(stage in stages)
        filters.getOrPut(stage) { mutableSetOf() }.add(filter)
    }

    public suspend fun process(context: Context) {
        for (stage in stages) {
            if (filters[stage] == null) continue

            for (filter in filters[stage]!!) {
                filter(context)

                if (!onFilter(context)) return
            }
        }
    }
}