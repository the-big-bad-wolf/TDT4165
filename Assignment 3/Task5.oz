declare LazyNumberGenerator in

    fun {LazyNumberGenerator StartValue }
        StartValue|fun {$}
            {LazyNumberGenerator StartValue+1}
        end
    end



{System.show {{{{{{LazyNumberGenerator 0}.2}.2}.2}.2}.2}.1 }