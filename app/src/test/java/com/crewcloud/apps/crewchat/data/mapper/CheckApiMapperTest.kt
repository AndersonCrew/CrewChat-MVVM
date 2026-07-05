package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.check_api.CheckApiDTO
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by BM Anderson on 4/7/26.
 */
class CheckApiMapperTest {
    @Test
    fun testCheckApiDtoToDomain() {
        val dto = CheckApiDTO(
            api = true
        )

        val domainModel = dto.toDomain()
        assertThat(domainModel.api).isEqualTo(dto.api)
    }
}