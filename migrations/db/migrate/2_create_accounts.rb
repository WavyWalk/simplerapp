class CreateAccounts < ActiveRecord::Migration[5.2]
  def change
    create_table :accounts do |t|
      t.references :user, foreign_key: true
      t.text :password
      t.string :email
      t.timestamp :created_at, null: true
      t.timestamp :updated_at, null: true
    end
  end
end
