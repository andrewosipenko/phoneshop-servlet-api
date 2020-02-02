package com.es.phoneshop.utils;

import java.util.Arrays;

public class IdSeeker
{
    public static long find(long[] ids)
    {
        if(ids.length == 0)
        {
            return 1L;
        }

        Arrays.sort(ids);

        for(int i = 0; i < ids.length - 1; i++)
        {
            if(ids[i + 1] - ids[i] > 1)
            {
                return ids[i] + 1;
            }
        }

        return ids[ids.length - 1] + 1;
    }
}
