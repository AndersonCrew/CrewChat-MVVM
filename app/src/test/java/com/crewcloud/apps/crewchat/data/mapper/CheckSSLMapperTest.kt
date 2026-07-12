package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.check_ssl.CheckSSLDTO
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by BM Anderson on 4/7/26.
 */
class CheckSSLMapperTest {
    @Test
    fun testCheckSSLDTOToDomain() {
        val dto = CheckSSLDTO(
            ssl = true
        )

        val domainModel = dto.toDomain()
        assertThat(domainModel.ssl).isTrue()
    }
}