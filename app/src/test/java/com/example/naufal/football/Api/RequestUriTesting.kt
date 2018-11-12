package com.example.naufal.football.Api

import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class RequestUriTesting {

    @Test
    fun doRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"

        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}