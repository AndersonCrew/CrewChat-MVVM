package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.login.CompanyLocationDTO
import com.crewcloud.apps.crewchat.data.dto.login.UserDTO
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity
import com.crewcloud.apps.crewchat.domain.model.CompanyLocation
import com.crewcloud.apps.crewchat.domain.model.User
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by BM Anderson on 4/7/26.
 */
class UserMapperTest {

    @Test
    fun companyLocationDTO_toDomain_mapsCorrectly() {
        val dto = CompanyLocationDTO(
            description = "Hanoi Office",
            locationNo = 123,
            latitude = 21.0285,
            longitude = 105.8542,
            isWorking = 0, // Theo code của bạn: isWorking == 0 -> true
            errorRange = 50
        )

        // Act (Thực hiện map)
        val domain = dto.toDomain()

        // Assert (Kiểm tra từng trường)
        assertThat(domain.description).isEqualTo("Hanoi Office")
        assertThat(domain.locationNo).isEqualTo(123)
        assertThat(domain.latitude).isEqualTo(21.0285)
        assertThat(domain.longitude).isEqualTo(105.8542)
        assertThat(domain.isWorking).isTrue() // Đảm bảo logic logic logic logic logic == 0 chạy đúng
        assertThat(domain.errorRange).isEqualTo(50)
    }

    @Test
    fun companyLocationDTO_toDomain_withNullValues_returnsDefaults() {
        // Arrange (DTO chứa toàn trường null)
        val dto = CompanyLocationDTO(
            description = null,
            locationNo = null,
            latitude = null,
            longitude = null,
            isWorking = null,
            errorRange = null
        )

        // Act
        val domain = dto.toDomain()

        assertThat(domain.description).isEmpty()
        assertThat(domain.locationNo).isEqualTo(0)
        assertThat(domain.latitude).isEqualTo(0.0)
        assertThat(domain.longitude).isEqualTo(0.0)
        assertThat(domain.isWorking).isFalse() // null != 0 -> false
        assertThat(domain.errorRange).isEqualTo(0)
    }

    // ==========================================
    // 2. TEST USER MAPPER
    // ==========================================

    @Test
    fun userDTO_toDomain_mapsCorrectly() {
        // Arrange
        val mockLocationDTO = CompanyLocationDTO(description = "HCM", locationNo = 1)
        val dto = UserDTO(
            userId = "admin",
            fullName = "Anderson",
            id = 99,
            avatar = "http://avatar.url",
            permissionType = 1,
            nameCompany = "CrewCloud",
            mailAddress = "anderson@crewcloud.com",
            informationCompany = listOf(mockLocationDTO),
            crewDdsServerIp = "192.168.1.1",
            crewChatFileServerIp = "192.168.1.2",
            crewDdsServerPort = 8080,
            crewChatFileServerPort = 9090,
            companyNo = 7,
            session = "xyz123"
        )

        // Act
        val domain = dto.toDomain()

        // Assert
        assertThat(domain.userId).isEqualTo("admin")
        assertThat(domain.fullName).isEqualTo("Anderson")
        assertThat(domain.id).isEqualTo(99)
        assertThat(domain.avatarUrl).isEqualTo("http://avatar.url")
        assertThat(domain.permissionType).isEqualTo(1)
        assertThat(domain.companyName).isEqualTo("CrewCloud")
        assertThat(domain.mailAddress).isEqualTo("anderson@crewcloud.com")
        assertThat(domain.companyLocations).hasSize(1)
        assertThat(domain.companyLocations[0].description).isEqualTo("HCM")
        assertThat(domain.crewDdsServerIp).isEqualTo("192.168.1.1")
        assertThat(domain.crewChatFileServerIp).isEqualTo("192.168.1.2")
        assertThat(domain.crewDdsServerPort).isEqualTo(8080)
        assertThat(domain.crewChatFileServerPort).isEqualTo(9090)
        assertThat(domain.companyNo).isEqualTo(7)
        assertThat(domain.session).isEqualTo("xyz123")
    }

    @Test
    fun userDTO_toDomain_withNullValues_returnsDefaults() {
        // Arrange
        val dto = UserDTO(
            userId = null, fullName = null, id = null, avatar = null, permissionType = null,
            nameCompany = null, mailAddress = null, informationCompany = null,
            crewDdsServerIp = null, crewChatFileServerIp = null, crewDdsServerPort = null,
            crewChatFileServerPort = null, companyNo = null, session = null
        )

        // Act
        val domain = dto.toDomain()

        // Assert
        assertThat(domain.userId).isEmpty()
        assertThat(domain.fullName).isEmpty()
        assertThat(domain.id).isEqualTo(0)
        assertThat(domain.companyLocations).isEmpty()
        assertThat(domain.crewDdsServerPort).isEqualTo(0)
        assertThat(domain.session).isEmpty()
    }

    // ==========================================
    // 3. TEST ENTITY TO DOMAIN & VICE VERSA (Đọc/Ghi DB)
    // ==========================================

    @Test
    fun user_toEntity_mapsCorrectly() {
        // Arrange
        val domainUser = User(
            userId = "id1", fullName = "Name", id = 1, avatarUrl = "url", permissionType = 2,
            companyName = "Comp", mailAddress = "mail",
            companyLocations = listOf(CompanyLocation("Office", 1, 0.0, 0.0, true, 1)),
            crewDdsServerIp = "ip", crewChatFileServerIp = "ip2", crewDdsServerPort = 1,
            crewChatFileServerPort = 2, companyNo = 5, session = "ss"
        )

        // Act
        val entity = domainUser.toEntity()

        // Assert
        assertThat(entity.userId).isEqualTo("id1")
        assertThat(entity.companyLocations[0].description).isEqualTo("Office")
    }

    @Test
    fun userEntity_toDomain_hardcodedFieldsAreEmpty() {
        // Arrange
        val entity = UserEntity(
            userId = "id1",
            fullName = "Name",
            id = 1,
            avatarUrl = "url",
            permissionType = 2,
            companyName = "Comp",
            mailAddress = "mail",
            companyLocations = emptyList(),
            companyNo = 5
        )

        // Act
        val domain = entity.toDomain()

        // Assert
        assertThat(domain.userId).isEqualTo("id1")
        assertThat(domain.crewDdsServerIp).isEmpty()
        assertThat(domain.crewChatFileServerPort).isEqualTo(0)
        assertThat(domain.session).isEmpty()
    }
}