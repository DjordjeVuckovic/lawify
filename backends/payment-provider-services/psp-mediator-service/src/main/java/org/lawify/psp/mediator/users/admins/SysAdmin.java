package org.lawify.psp.mediator.users.admins;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lawify.psp.mediator.users.PspRole;
import org.lawify.psp.mediator.users.UserBase;

@Entity
@Table(name = "sys_admins", indexes = {
        @Index(name = "admin_email_index", columnList = "email")
})
@NoArgsConstructor
public class SysAdmin extends UserBase {
    public SysAdmin(String email,String password) {
        this.email = email;
        this.password = password;
        addRole(PspRole.ADMIN);
    }
}
