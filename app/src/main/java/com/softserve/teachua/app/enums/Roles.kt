package com.softserve.teachua.app.enums

private val roles = listOf("ВІДВІДУВАЧ", "КЕРІВНИК", "АДМІНІСТРАТОР")


class Role {
    fun getUaRoleName(roleName: String):String {
        return when (roleName) {
            "ROLE_USER" -> roles[0]
            "ROLE_MANAGER" -> roles[1]
            "ROLE_ADMIN" -> roles[2]
            else -> {return "null"}
        }
    }

    fun getRoleName(roleName: String): String {
        return when (roleName) {
            roles[0] -> return RoleStatus.ROLE_USER.name
            roles[1] -> return RoleStatus.ROLE_MANAGER.name
            roles[2] -> return RoleStatus.ROLE_ADMIN.name
            else -> {return "null"}
        }
    }

    enum class RoleStatus {
        ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
    }
//
//    companion object{
//        fun user():Role{
//            return Role(RoleStatus.ROLE_USER, roles[0])
//        }
//        fun manager():Role{
//            return Role(RoleStatus.ROLE_MANAGER, roles[1])
//        }
//        fun admin():Role{
//            return Role(RoleStatus.ROLE_ADMIN, roles[2])
//        }
//    }
}