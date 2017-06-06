package com.diyanfilipov.domain.jpa

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import org.hibernate.annotations.GenericGenerator
import org.joda.time.DateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
@ToString(includeNames = true, includePackage = false)
@EntityListeners(AuditingEntityListener)
abstract class UuidPersistable implements Persistable<String> {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  String id

  @Version
  Long version

  @CreatedDate
  DateTime dateCreated

  @LastModifiedDate
  DateTime lastUpdated

  public boolean isNew() {
    id == null
  }


  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false
    }

    if (this.class != obj.class) {
      return false
    }

    this.id == null ? false : this.id == obj.id

  }

  @Override
  public int hashCode() {
    int hashCode = 17
    hashCode + ((this.id == null) ? 0 : this.id.hashCode() * 31)
  }

}
