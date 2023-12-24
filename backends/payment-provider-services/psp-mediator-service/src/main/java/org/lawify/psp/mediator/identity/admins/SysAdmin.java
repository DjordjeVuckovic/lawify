package org.lawify.psp.mediator.identity.admins;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.lawify.psp.mediator.identity.PspRole;
import org.lawify.psp.mediator.identity.UserBase;

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
