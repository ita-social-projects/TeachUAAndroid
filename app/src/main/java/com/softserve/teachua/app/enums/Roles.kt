package com.softserve.teachua.app.enums

private val roles = listOf("ВІДВІДУВАЧ", "АДМІНІСТРАТОР", "КЕРІВНИК")


data class Role(
    val role:RoleStatus,
    val uaName:String
)
{
    enum class RoleStatus{
        USER, MANAGER, ADMIN
    }

    companion object{
        fun user():Role{
            return Role(RoleStatus.USER, roles[0])
        }
        fun manager():Role{
            return Role(RoleStatus.MANAGER, roles[1])
        }
        fun admin():Role{
            return Role(RoleStatus.ADMIN, roles[2])
        }
    }
}